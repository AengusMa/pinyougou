package com.pinyougou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * @author mawenlong
 * @date 2018/08/26
 * describe:
 */
@Component
public class ItemSearchListener implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;
        String text = null;
        try {
            text = textMessage.getText();
            System.out.println("监听获取到消息："+text);
            List<TbItem> itemList = JSON.parseArray(text,TbItem.class);
            itemSearchService.importList(itemList);
            System.out.println("执行索引库导入");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
