package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.base.PushModel;
import com.wuxiankeneng.jian.bean.card.OrderCard;
import com.wuxiankeneng.jian.bean.db.Order;
import com.wuxiankeneng.jian.utils.PushDispatcher;
import com.wuxiankeneng.jian.utils.TextUtil;

public class PushFactory {
    //推送一个订单
    public static void pushNewOrder(Order order) {
        if (order == null)
            return;
        OrderCard card = new OrderCard(order);
        //要推送的json字符串
        String entity = TextUtil.toJson(card);
        //发送者(给商家推送)
        PushDispatcher dispatcher = new PushDispatcher(PushDispatcher.PUSH_TYPE_TRADER);
        // 推送的真实Model
        PushModel pushModel = new PushModel();
        // 每一条历史记录都是独立的，可以单独的发送
        pushModel.add(PushModel.ENTITY_TYPE_ORDER, entity);
        // 把需要发送的数据，丢给发送者进行发送
        boolean b = dispatcher.add(order.getShop().getCreator(), pushModel);
        //参数问题
        if (!b)
            return;
        //提交
        dispatcher.submit();
    }

    //商家推送一个订单给学生(更新后)
    public static void pushOrderForStu(Order order){
        if (order==null)
            return;

        OrderCard card = new OrderCard(order);
        //要推送的json字符串
        String entity = TextUtil.toJson(card);

        //发送者(给学生推送)
        PushDispatcher dispatcher = new PushDispatcher(PushDispatcher.PUSH_TYPE_STUDENT);
        // 推送的真实Model
        PushModel pushModel = new PushModel();
        // 每一条历史记录都是独立的，可以单独的发送
        pushModel.add(PushModel.ENTITY_TYPE_ORDER, entity);
        // 把需要发送的数据，丢给发送者进行发送
       boolean b = dispatcher.add(order.getStudent(), pushModel);
       //参数问题
       if (!b)
           return;
        //提交
        dispatcher.submit();
    }
}
