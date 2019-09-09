package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.api.order.CreateOrderModel;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.OrderCard;
import com.wuxiankeneng.jian.bean.db.Order;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.factory.OrderFactory;
import com.wuxiankeneng.jian.factory.PushFactory;
import com.wuxiankeneng.jian.factory.ShopFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("student")
public class StudentOrderService extends BaseService {

    //学生提交订单
    @POST
    @Path("commitOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<OrderCard> commitOrder(CreateOrderModel model) {
        if (!CreateOrderModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        Student sender = getStudentSelf();
        Shop shop = ShopFactory.findById(model.getShopId());
        if (shop == null)
            return ResponseModel.buildParameterError();
        //订单是否存在
        Object o = OrderFactory.isExist(model.getId());
        if (o != null)
            return ResponseModel.buildHaveOrderError();

        //创建订单
        Order order = OrderFactory.create(sender, shop, model);
        if (order == null) {
            //创建失败
            return ResponseModel.buildServiceError();
        }

        // 进行推送
        PushFactory.pushNewOrder(order);

        return ResponseModel.buildOk(new OrderCard(order));
    }

    @GET
    @Path("getOrders")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<OrderCard>> getOrders() {
        Student student = getStudentSelf();
        List<Order> orders = OrderFactory.findOrders(student);
        if (orders == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(orders.stream().map(OrderCard::new).collect(Collectors.toList()));
    }

    @GET
    @Path("getOrder/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<OrderCard> getOrderById(@PathParam("orderId") String orderId) {
        if (Strings.isNullOrEmpty(orderId))
            return ResponseModel.buildParameterError();

        Order order = OrderFactory.findById(orderId);
        if (order == null)
            return ResponseModel.buildServiceError();
        if (!getStudentSelf().getId().equals(order.getStudent().getId()))
            return ResponseModel.buildParameterError();//这儿返回的错误是订单的学生没对上
        return ResponseModel.buildOk(new OrderCard(order));
    }
}
