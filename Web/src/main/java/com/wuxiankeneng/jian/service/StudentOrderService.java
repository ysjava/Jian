package com.wuxiankeneng.jian.service;

import com.wuxiankeneng.jian.bean.api.order.CreateOrderModel;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.OrderCard;
import com.wuxiankeneng.jian.bean.db.Order;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.factory.OrderFactory;
import com.wuxiankeneng.jian.factory.PushFactory;
import com.wuxiankeneng.jian.factory.ShopFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        if (o!=null)
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
}
