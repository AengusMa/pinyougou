package com.pinyougou.sellergoods.service;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/8/10
 * describe: 服务层接口
 */
public interface SpecificationService {
    /**
     * 返回全部商品列表
     *
     * @param
     * @return java.util.List<com.pinyougou.pojo.TbSpecification>
     */
    List<TbSpecification> getAll();
    /**
     * 添加
     *
     * @param specification
     * @return void
     */
    void add(Specification specification);
    /**
     * 修改
     *
     * @param specification
     * @return void
     */
    void update(Specification specification);
    /**
     * 根据主键id进行查询
     *
     * @param id
     * @return com.pinyougou.pojo.TbSpecification
     */
    Specification getById(Long id);
    /**
     * 删除多个商品
     *
     * @param ids
     * @return void
     */
    void delete(Long [] ids);
    /**
     * 条件查询并分页
     *
     * @param specification
     * @param pageNum
     * @param size
     * @return com.common.pojo.PageResult
     */
    PageResult getPage(TbSpecification specification, int pageNum, int size);
}
