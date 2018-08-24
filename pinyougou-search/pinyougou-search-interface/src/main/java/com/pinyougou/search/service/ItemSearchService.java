package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    /**
     * 搜索方法
     *      
     * @param searchMap
     * @return java.util.Map
     */
    Map search(Map searchMap);
    /**
     * 导入列表
     *      
     * @param list
     * @return void
     */
    void importList(List list);
    /**
     * 根据id删除商品列表的solr索引
     *      
     * @param ids
     * @return void
     */
    void deleteByGoodsByIds(List ids);
}
