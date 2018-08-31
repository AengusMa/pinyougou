package com.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/createNative")
    public Map createNative() {
        //1.获取当前登录用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //2.提取秒杀订单（从缓存 ）
        TbSeckillOrder seckillOrder = seckillOrderService.searchOrderFromRedisByUserId(username);
        //3.调用微信支付接口
        if (seckillOrder != null) {
            System.out.println("调用支付接口");
            return null;
        } else {
            return new HashMap<>();
        }
    }


    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        Result result = new Result(true, "支付成功");

        return result;
    }


}
