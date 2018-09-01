package com.pinyougou.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author mawenlong
 * @date 2018/09/01
 * describe:秒杀任务调度
 */
@Component
public class TestTask {


    @Scheduled(cron = "* * * * * ?")
    public void refreshSeckillGoods() {
        System.out.println("执行了任务调度" + new Date());
    }

}
