package com.pinyougou.sellergoods.service;

import com.common.pojo.PageResult;
import com.pinyougou.pojo.TbItemCat;

import java.util.List;
/**
 * @author mawenlong
 * @date 2018/8/12
 * describe: 商品分类
 */
public interface ItemCatService {

	/**
	 * 返回全部列表
	 *
	 * @param
	 * @return java.util.List<com.pinyougou.pojo.TbBrand>
	 */
	List<TbItemCat> findAll();
	/**
	 * 添加
	 *
	 * @param itemCat
	 * @return void
	 */
	void add(TbItemCat itemCat);
	/**
	 * 根据id查询
	 *
	 * @param id
	 * @return com.pinyougou.pojo.TbItemCat
	 */
	TbItemCat getById(long id);
	/**
	 * 修改
	 * @param itemCat
	 * @return void
	 */
	void update(TbItemCat itemCat);
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
	 * @param itemCat
	 * @param pageNum
	 * @param pageSize
	 * @return com.common.pojo.PageResult
	 */
	PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize);
	/**
	 * 分局上级id查询商品分类列表
	 *      
	 * @param parentId
	 * @return java.util.List<com.pinyougou.pojo.TbItemCat>
	 */
	List<TbItemCat> getByParentId(Long parentId);
	
}
