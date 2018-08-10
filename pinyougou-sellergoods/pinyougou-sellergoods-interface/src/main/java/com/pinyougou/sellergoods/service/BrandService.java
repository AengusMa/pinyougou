package com.pinyougou.sellergoods.service;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/8/10
 * describe: 品牌接口
 */
public interface BrandService {
    /**
     * 返回全部列表
     *
     * @param 
     * @return java.util.List<com.pinyougou.pojo.TbBrand>
     */
    List<TbBrand> findAll();
    /**
     * 添加
     *
     * @param brand
     * @return void
     */
    void add(TbBrand brand);
    /**
     * 根据id查询
     *
     * @param id
     * @return com.pinyougou.pojo.TbBrand
     */
    TbBrand getById(long id);
    /**
     * 修改
     * @param brand
     * @return void
     */
    void update(TbBrand brand);
    /**
     * 删除多个
     *
     * @param ids
     * @return void
     */
    void delete(Long[] ids);
    /**
     * 根据条件查询并且进行分页
     *
     * @param brand
	 * @param pageNum
	 * @param pageSize
     * @return com.common.pojo.PageResult
     */
    PageResult findPage(TbBrand brand,int pageNum, int pageSize);
    /**
     * 返回下拉列表数据
     *      
     * @param
     * @return java.util.List<Map>
     */
    List<Map> selectOptionList();
}
