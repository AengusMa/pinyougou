package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.common.pojo.PageResult;
import com.common.pojo.Result;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
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

    @Reference(timeout = 100000)


    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.getAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.getPage(null, page, rows);
    }

    //@RequestMapping("/add")
    //public Result add(@RequestBody TbGoods goods){
    //    try {
    //        goodsService.add(goods);
    //        return new Result(true, "增加成功");
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return new Result(false, "增加失败");
    //    }
    //}
    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods) {
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

    @Autowired
    private Destination queueSolrDeleteDestination;
    @Autowired
    private Destination topicPageDeleteDestination;
    @RequestMapping("/delete")
    public Result delete(final Long[] ids) {
        try {
            goodsService.delete(ids);
            //删除索引库的数据
            jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });
            //删除每个服务器上的商品详情页

            jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(sellerId);
        return goodsService.getPage(goods, page, rows);
    }

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queueSolrDestination;
    @Autowired
    private Destination topicPageDestination;


    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            goodsService.updateStatus(ids, status);
            //审核通过
            if ("1".equals(status)) {
                final List<TbItem> itemList = goodsService.findItemListByGoodsAndStatus(ids, status);
                //更新solr索引库(消息)
                final String jsonString = JSON.toJSONString(itemList);
                jmsTemplate.send(queueSolrDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(jsonString);
                    }
                });

                //生成商品详情页(消息)
                for (final Long id : ids) {
                    //itemPageService.genItemHtml(id);
                    jmsTemplate.send(topicPageDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(id+"");
                        }
                    });
                }

            }
            return new Result(true, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新失败");
        }
    }

    //@Reference(timeout = 10000)
    //private ItemPageService itemPageService;
    //
    ///**
    // * 测试时候使用
    // *
    // * @param goodsId
    // * @return void
    // */
    //@RequestMapping("/genHtml")
    //public void genHtml(Long goodsId) {
    //    itemPageService.genItemHtml(goodsId);
    //}
}
