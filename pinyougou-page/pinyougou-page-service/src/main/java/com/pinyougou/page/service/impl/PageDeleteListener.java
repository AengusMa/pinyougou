package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author mawenlong
 * @date 2018/08/26
 * describe:
 */
@Component
public class PageDeleteListener implements MessageListener {
    @Autowired
    private ItemPageService itemPageService;
    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage)message;
        Long[] ids = null;
        try {
            ids = (Long[])objectMessage.getObject();
            System.out.println("监听获取到消息："+ids);
            itemPageService.deleteItemHtml(ids);
            System.out.println("执行商品详情页删除");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
