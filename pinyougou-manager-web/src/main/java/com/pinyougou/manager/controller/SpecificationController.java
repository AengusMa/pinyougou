package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.pojo.PageResult;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/10
 * describe:
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;
    @RequestMapping("/findAll")
    public List<TbSpecification> findAll(){
        return specificationService.getAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows){
        return specificationService.getPage(null,page, rows);
    }
    @RequestMapping("/add")
    public Result add(@RequestBody Specification specification){
        try {
            specificationService.add(specification);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }
    @RequestMapping("/update")
    public Result update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }
    @RequestMapping("/findOne")
    public Specification findOne(Long id){
        return specificationService.getById(id);
    }
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            specificationService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSpecification specification, int page, int rows  ){
        return specificationService.getPage(specification, page, rows);
    }
}
