package com.pinyougou.sellergoods.service;

import java.util.List;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbBrand;

/**
 * 品牌接口
 */
public interface BrandService {

    List<TbBrand> findAll();
//    PageResult findPage(int pageNum, int pageSize);
    void add(TbBrand brand);
    TbBrand getById(long id);
    void update(TbBrand brand);
    void delete(Long[] ids);
    PageResult findPage(TbBrand brand,int pageNum, int pageSize);
}
