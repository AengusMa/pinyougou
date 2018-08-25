package com.pinyougou.page.service;

/**
 * @author mawenlong
 * @date 2018/08/25
 * describe:
 */
public interface ItemPageService {

    /**
     * 生成商品详细页
     *
     * @param goodsId
     * @return boolean
     */
    boolean genItemHtml(Long goodsId);

    /**
     * 删除商品详情页
     *
     * @param goodsId
     * @return
     */
    boolean deleteItemHtml(Long[] goodsId);
}
