package com.pinyougou.sellergoods.service;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/8/10
 * describe: 商品服务层接口
 */
public interface GoodsService {
    /**
     * 返回全部商品列表
     *      
     * @param 
     * @return java.util.List<com.pinyougou.pojo.TbGoods>
     */
     List<TbGoods> getAll();
     /**
      * 添加
      *      
      * @param goods
      * @return void
      */
     void add(Goods goods);
     /**
      * 修改
      *      
      * @param goods
      * @return void
      */
     void update(Goods goods);
     /**
      * 根据主键id进行查询
      *      
      * @param id
      * @return com.pinyougou.pojo.Goods
      */
     Goods getById(Long id);
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
      * @param goods
	  * @param pageNum
	  * @param size
      * @return com.common.pojo.PageResult
      */
     PageResult getPage(TbGoods goods, int pageNum, int size);
}
