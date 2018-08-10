package com.pinyougou.sellergoods.service;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbTypeTemplate;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/10
 * describe:商品类型模板管理
 */
public interface TypeTemplateService {
    /**
     * 返回全部列表
     *
     * @param
     * @return java.util.List<com.pinyougou.pojo.TypeTemplate>
     */
    List<TbTypeTemplate> getAll();
    /**
     * 添加
     *
     * @param typeTemplate
     * @return void
     */
    void add(TbTypeTemplate typeTemplate);
    /**
     * 修改
     *
     * @param typeTemplate
     * @return void
     */
    void update(TbTypeTemplate typeTemplate);
    /**
     * 根据主键id进行查询
     *
     * @param id
     * @return com.pinyougou.pojo.TbTypeTemplate
     */
    TbTypeTemplate getById(Long id);
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
     * @param typeTemplate
     * @param pageNum
     * @param size
     * @return com.common.pojo.PageResult
     */
    PageResult getPage(TbTypeTemplate typeTemplate, int pageNum, int size);
}
