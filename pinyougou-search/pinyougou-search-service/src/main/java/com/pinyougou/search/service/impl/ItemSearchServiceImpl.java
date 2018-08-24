package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/08/21
 * describe:
 */
@Service(timeout = 5000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map search(Map searchMap) {
        Map map = new HashMap();
        //空格处理
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords", keywords.replace(" ", ""));

        //查询列表
        map.putAll(searchList(searchMap));
        //分组查询商品分裂列表
        List<String> categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);
        //查询品牌和规格
        String category = (String) searchMap.get("category");
        if (!category.equals("")) {
            map.putAll(searchBrandAndSpecList(category));
        } else {
            if (categoryList.size() > 0) {
                map.putAll(searchBrandAndSpecList(categoryList.get(0)));
            }
        }
        return map;
    }

    //查询列表
    private Map searchList(Map searchMap) {
        Map map = new HashMap();
        //**************高亮选项设置*******************
        HighlightQuery query = new SimpleHighlightQuery();
        //构建高亮显示对象
        HighlightOptions options = new HighlightOptions().addField("item_title");
        options.setSimplePrefix("<em style='color:red'>");
        options.setSimplePostfix("</em>");
        query.setHighlightOptions(options);
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //按商品分类过滤
        if (!"".equals(searchMap.get("category"))) {
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }
        //按品牌分类过滤
        if (!"".equals(searchMap.get("brand"))) {
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }
        //按规格过滤
        if (searchMap.get("spec") != null) {
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_spec_" + key).is(searchMap.get(key));
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
        //按价格过滤
        if (!"".equals(searchMap.get("price"))) {
            String priceStr = (String) searchMap.get("price");
            String prices[] = priceStr.split("-");
            if (!prices[0].equals("0")) {
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(prices[0]);
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if (!prices[1].equals("*")) {
                FilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_price").lessThanEqual(prices[1]);
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
        //分页
        Integer pageNo = (Integer) searchMap.get("pageNo");
        if (pageNo != null) {
            pageNo = 1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageSize != null) {
            pageSize = 20;
        }
        //起始索引
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);
        //按价格排序
        String sortValue = (String) searchMap.get("sort");
        String sortField = (String) searchMap.get("sortField");

        if (sortValue != null && !sortValue.equals("")) {
            Sort sort = null;
            if ("ASC".equals(sortValue)) {
                sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
            }
            if ("DESC".equals(sortValue)) {
                sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
            }
            query.addSort(sort);
        }


        //**************获取高亮结果集*******************
        //返回高亮页对象
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
        //每条记录的高亮
        List<HighlightEntry<TbItem>> entryList = page.getHighlighted();
        for (HighlightEntry<TbItem> entry : entryList) {
            //  获取高亮列表（高亮域的个数）
            List<HighlightEntry.Highlight> highlights = entry.getHighlights();
            ////每个域有可能存储多值
            //for (HighlightEntry.Highlight h : highlights) {
            //    List<String> sns = h.getSnipplets();
            //    System.out.println(sns);
            //}
            if (highlights.size() > 0 &&
                    highlights.get(0).getSnipplets().size() > 0) {
                TbItem item = entry.getEntity();
                item.setTitle(highlights.get(0).getSnipplets().get(0));
            }
        }
        map.put("rows", page.getContent());
        map.put("totalPages", page.getTotalPages());
        map.put("total", page.getTotalElements());
        return map;
    }

    /**
     * 根据关键字查询商品分类列表
     *
     * @param
     * @return java.util.List
     */
    private List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList<>();
        Query query = new SimpleQuery("*:*");
        //根据关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //设置分组选项
        GroupOptions options = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(options);
        //设置分组页
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
        //获取分组结果对象
        GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
        //获取分组入口
        Page<GroupEntry<TbItem>> groupEntryPage = groupResult.getGroupEntries();
        //获取分组入口集合
        List<GroupEntry<TbItem>> entryList = groupEntryPage.getContent();
        for (GroupEntry<TbItem> entry : entryList) {
            list.add(entry.getGroupValue());
        }
        return list;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据品分类名称查询品牌和规格列表
     *
     * @param categoryName
     * @return java.util.Map
     */
    private Map searchBrandAndSpecList(String categoryName) {
        Map map = new HashMap();
        //根据分类名称获取模板id
        Long templateId = (Long) redisTemplate.boundHashOps("itemCat").get(categoryName);
        if (templateId != null) {
            //根据模板id获取品牌列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(templateId);
            map.put("brandList", brandList);
            //根据模板id获取规格列表
            List specList = (List) redisTemplate.boundHashOps("specList").get(templateId);
            map.put("specList", specList);
        }

        return map;
    }


}
