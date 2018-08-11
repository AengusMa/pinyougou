package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.pojo.PageResult;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/11
 * describe:
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    @RequestMapping("/findAll")
    public List<TbSeller> findAll(){
        return sellerService.getAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows){
        return sellerService.getPage(null,page, rows);
    }
    @RequestMapping("/add")
    public Result add(@RequestBody TbSeller seller){
        try {
            sellerService.add(seller);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }
    @RequestMapping("/update")
    public Result update(@RequestBody TbSeller seller){
        try {
            sellerService.update(seller);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }
    @RequestMapping("/findOne")
    public TbSeller findOne(String id){
        return sellerService.getById(id);
    }
    @RequestMapping("/delete")
    public Result delete(String [] ids){
        try {
            sellerService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
        return sellerService.getPage(seller, page, rows);
    }


}
