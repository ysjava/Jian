package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.OrderCard;
import com.wuxiankeneng.jian.bean.db.Order;
import com.wuxiankeneng.jian.factory.OrderFactory;
import com.wuxiankeneng.jian.factory.PushFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("trader")
public class TraderOrderService {
        //商家更新状态
    @PUT
    @Path("update/orderState/{state}|{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<OrderCard> update(@PathParam("state") int state, @PathParam("orderId") String orderId) {
        if (state != Order.STATE_PROCESSING && state != Order.STATE_ACCEPT && state != Order.STATE_DONE && Strings.isNullOrEmpty(orderId)) {
            return ResponseModel.buildParameterError();
        }
        Order order = OrderFactory.findById(orderId);
        if (order == null)
            return ResponseModel.buildError("没找到订单信息");

        order = OrderFactory.update(state, order);
        if (order == null)
            return ResponseModel.buildServiceError();
        //推送
        PushFactory.pushOrderForStu(order);
        return ResponseModel.buildOk(new OrderCard(order));
    }
}
