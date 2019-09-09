package com.wuxiankeneng.jian.factory;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.wuxiankeneng.jian.bean.api.order.CreateOrderModel;
import com.wuxiankeneng.jian.bean.db.*;
import com.wuxiankeneng.jian.utils.Hib;
import com.wuxiankeneng.jian.utils.TextUtil;

import java.util.List;

public class OrderFactory {
    //用id查询订单
    public static Order findById(String id) {
        return Hib.query(session -> session.get(Order.class, id));
    }

    //该查询仅用于判断是否存在订单信息,只查询id
    public static Object isExist(String id) {
        return Hib.query(session -> session.createQuery("select id from Order where id=:id")
                .setParameter("id", id)
                .uniqueResult());
    }

    public static Order create(Student student, Shop shop, CreateOrderModel model) {
        Order order = new Order();
        order.setId(model.getId());
        order.setEntity(TextUtil.toJson(model.getGoodsCards()));

        order.setStudent(student);
        order.setShop(shop);
        order.setDeliveryPrice(shop.getDeliveryPrice());
        Address address = StudentFactory.findAddressById(model.getAddressCard().getId());
        if (address == null)
            return null;
        order.setAddress(address);
        order.setState(Order.STATE_PROCESSING);
        return saveOrder(order);
    }

    private static Order saveOrder(Order order) {
        return Hib.query(session -> {
            session.save(order);
            //写入到数据库
            session.flush();
            //马上查询出来
            session.refresh(order);
            return order;
        });
    }

    //更新订单的状态
    public static Order update(int state, Order order) {
        return Hib.query(session -> {
            order.setState(state);
            session.saveOrUpdate(order);
            session.flush();
            session.refresh(order);
            return order;
        });
    }

    @SuppressWarnings("unchecked")
    public static List<Order> findOrders(Student student) {
       return Hib.query(session -> session.createQuery("from Order where student=:student order by createAt desc ")
                .setParameter("student", student)
                .list());
    }
}
