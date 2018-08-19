package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.common.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/08/10
 * describe:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    @Override
    public List<TbSpecification> getAll() {
        return specificationMapper.selectByExample(null);
    }

    @Override
    public void add(Specification specification) {
        TbSpecification tbSpecification = specification.getSpecification();
        //保存规格实体
        specificationMapper.insert(tbSpecification);
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption option : specificationOptionList) {
            //保存规格选项
            option.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(option);
        }
    }

    @Override
    public void update(Specification specification) {

        TbSpecification tbSpecification = specification.getSpecification();
        //保存规格实体
        specificationMapper.updateByPrimaryKey(tbSpecification);

        //删除原来规格对应的规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        specificationOptionMapper.deleteByExample(example);

        //保存规格选项
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption option : specificationOptionList) {
            option.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(option);
        }
    }

    @Override
    public Specification getById(Long id) {
        Specification res = new Specification();
        //获取规格实体
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        res.setSpecification(tbSpecification);
        //获取规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
        res.setSpecificationOptionList(options);

        return res;
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            //删除过个表
            specificationMapper.deleteByPrimaryKey(id);
            //删除规格选项数据
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }

    @Override
    public PageResult getPage(TbSpecification specification, int pageNum, int size) {
        PageHelper.startPage(pageNum, size);

        TbSpecificationExample example = new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();

        if (specification != null) {
            if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }
        }
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getResult(), page.getTotal());
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
