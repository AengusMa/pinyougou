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
}
