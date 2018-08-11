package com.pinyougou.sellergoods.service;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbSeller;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/8/10
 * describe: 商家服务层接口
 */
public interface SellerService {
    /**
     * 返回全部商品列表
     *
     * @param
     * @return java.util.List<com.pinyougou.pojo.TbSeller>
     */
    List<TbSeller> getAll();
    /**
     * 添加
     *
     * @param seller
     * @return void
     */
    void add(TbSeller seller);
    /**
     * 修改
     *
     * @param seller
     * @return void
     */
    void update(TbSeller seller);
    /**
     * 根据主键id进行查询
     *
     * @param id
     * @return com.pinyougou.pojo.TbSeller
     */
    TbSeller getById(String id);
    /**
     * 删除多个商品
     *
     * @param ids
     * @return void
     */
    void delete(String [] ids);
    /**
     * 条件查询并分页
     *
     * @param seller
     * @param pageNum
     * @param size
     * @return com.common.pojo.PageResult
     */
    PageResult getPage(TbSeller seller, int pageNum, int size);
}