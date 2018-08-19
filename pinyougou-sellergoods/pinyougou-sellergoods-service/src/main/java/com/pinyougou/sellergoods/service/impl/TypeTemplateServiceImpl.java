package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.common.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/08/11
 * describe:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired
    private TbTypeTemplateMapper typeTemplateMapper;

    @Override
    public List<TbTypeTemplate> getAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    @Override
    public void add(TbTypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }

    @Override
    public void update(TbTypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }

    @Override
    public TbTypeTemplate getById(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult getPage(TbTypeTemplate typeTemplate, int pageNum, int size) {
        PageHelper.startPage(pageNum, size);

        TbTypeTemplateExample example = new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria = example.createCriteria();

        if (typeTemplate != null) {
            if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
                criteria.andNameLike("%" + typeTemplate.getName() + "%");
            }
            if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
                criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
            }
            if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
                criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
            }
            if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
                criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
            }
        }
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(example);
        return new PageResult(page.getResult(), page.getTotal());
    }
    @Autowired
    private TbSpecificationOptionMapper optionMapper;
    @Override
    public List<Map> getSpecList(Long id) {
        //查询模板
        TbTypeTemplate tbTypeTemplate = typeTemplateMapper.selectByPrimaryKey(id);

        List<Map> list = JSON.parseArray(tbTypeTemplate.getSpecIds(),Map.class);
        for(Map map :list){
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));
            List<TbSpecificationOption> options = optionMapper.selectByExample(example);
            map.put("options",options);
        }
        return list;
    }
}
