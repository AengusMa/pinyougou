package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.pojo.PageResult;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/10
 * describe: 商品控制层
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.getAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.getPage(null, page, rows);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.getGoods().setSellerId(sellerId);
        try {
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods) {
        //当前商家id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //判断商品是否为该商家的商品
        Goods goods1 = goodsService.getById(goods.getGoods().getId());
        if (!goods1.getGoods().getSellerId().equals(sellerId) ||
                !goods.getGoods().getSellerId().equals(sellerId)) {
            return new Result(false, "非法操作");
        }
        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.getById(id);
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        return goodsService.getPage(goods, page, rows);
    }
}
