package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.pojo.PageResult;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
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
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;
    @RequestMapping("/findAll")
    public List<TbTypeTemplate> findAll() {
        return typeTemplateService.getAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size) {
        return typeTemplateService.getPage(null, page, size);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.add(typeTemplate);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/getById")
    public TbTypeTemplate getById(Long id) {
        return typeTemplateService.getById(id);
    }


    @RequestMapping("/update")
    public Result update(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            typeTemplateService.delete(ids);
            return new Result(true, "删除成功1");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TbTypeTemplate typeTemplate, int page, int size) {
        return typeTemplateService.getPage(typeTemplate, page, size);
    }
}
