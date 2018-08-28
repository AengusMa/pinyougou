package com.pinyougou.cart.service;

import com.pinyougou.pojogroup.Cart;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/28
 * describe: 购物车服务接口
 */
public interface CartService {
    /**
     * 添加商品到购物车
     *      
     * @param cartList
	 * @param itemId
	 * @param num
     * @return java.util.List<com.pinyougou.pojogroup.Cart>
     */
    List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num );
    /**
     * 将购物车保存到redis
     *      
     * @param username
	 * @param cartList
     * @return void
     */
     void saveCartListToRedis(String username,List<Cart> cartList);
     /**
      * 从redis中查询购物车
      *      
      * @param username
      * @return java.util.List<com.pinyougou.pojogroup.Cart>
      */
     List<Cart> findCartListFromRedis(String username);
    /**
     * 合并购物车
     *      
     * @param cartList1
	 * @param cartList2
     * @return java.util.List<com.pinyougou.pojogroup.Cart>
     */
     List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);
}
