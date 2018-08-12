package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.common.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsExample;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/10
 * describe:商品服务实现
 */
@Service
public class GoodServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Override
    public List<TbGoods> getAll() {
        return goodsMapper.selectByExample(null);
    }

    @Override
    public void add(Goods goods) {
        //设置状态未审核
        goods.getGoods().setAuditStatus("0");
        //添加商品基本信息
        goodsMapper.insert(goods.getGoods());
        //添加商品扩展信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insert(goods.getGoodsDesc());
    }

    @Override
    public void update(Goods goods) {
        //goodsMapper.updateByPrimaryKey(goods);
    }

    @Override
    public Goods getById(Long id) {
        Goods goods = new Goods();
        goods.setGoods(goodsMapper.selectByPrimaryKey(id));
        goods.setGoodsDesc(goodsDescMapper.selectByPrimaryKey(id));
        return goods;
    }

    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            goodsMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult getPage(TbGoods goods, int pageNum, int size) {
        PageHelper.startPage(pageNum,size);
        TbGoodsExample example = new TbGoodsExample();
        if(goods!=null){
            TbGoodsExample.Criteria criteria = example.createCriteria();
            if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
                criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
            }
            if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
                criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
            }
            if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
                criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
            }
            if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
                criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
            }
            if(goods.getCaption()!=null && goods.getCaption().length()>0){
                criteria.andCaptionLike("%"+goods.getCaption()+"%");
            }
            if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
                criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
            }
            if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
                criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
            }
            if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
                criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
            }
        }
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getResult(),page.getTotal());
    }
}
