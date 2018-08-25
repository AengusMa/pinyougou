package com.pinyougou.search.service.impl;

import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Arrays;

/**
 * @author mawenlong
 * @date 2018/08/26
 * describe:
 */
public class ItemDeleteListener implements MessageListener {
    @Autowired
    private ItemSearchService itemSearchService;
    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage)message;
        Long[] ids = null;
        try {
            ids = (Long[])objectMessage.getObject();
            System.out.println("监听获取到消息："+ids);
            itemSearchService.deleteByGoodsByIds(Arrays.asList(ids));
            System.out.println("执行索引库删除");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
