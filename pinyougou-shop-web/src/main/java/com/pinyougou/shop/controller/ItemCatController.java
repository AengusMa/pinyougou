package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.pojo.PageResult;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/12
 * describe:商品分类
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/findAll")
    public List<TbItemCat> findAll() {
        return itemCatService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size) {
        return itemCatService.findPage(null, page, size);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbItemCat itemCat) {
        try {
            itemCatService.add(itemCat);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/findOne")
    public TbItemCat findOne(Long id) {
        return itemCatService.getById(id);
    }


    @RequestMapping("/update")
    public Result update(@RequestBody TbItemCat itemCat) {
        try {
            itemCatService.update(itemCat);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            itemCatService.delete(ids);
            return new Result(true, "删除成功1");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TbItemCat itemCat, int page, int size) {
        return itemCatService.findPage(itemCat, page, size);
    }
    @RequestMapping("/getByParentId")
    public List<TbItemCat> getByParentId(Long parentId) {
        return itemCatService.getByParentId(parentId);
    }

}
