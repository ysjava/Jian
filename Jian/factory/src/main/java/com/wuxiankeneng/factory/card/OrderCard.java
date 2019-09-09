package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Order;

import java.util.Date;

/**
 * Created by White paper on 2019/9/1
 * Describe :
 */
public class OrderCard {
    private String id;
    private String entity;
    //学生名字
    private String s_name;
    //学生手机(收货电话
    private String s_phone;
    //商店名字
    private String shopName;
    //订单创建时间
    private Date createAt;
    //配送信息
    private AddressCard address;
    //订单状态
    private int state;
    //店铺图标
    private String shopIon;
    //配送费
    private String deliveryPrice;

    public Order build() {
        Order order= new Order();
        order.setoId(id);
        order.setEntity(entity);
        order.setS_name(s_name);
        order.setS_phone(s_phone);
        order.setShopName(shopName);
        order.setCreateAt(createAt);
        order.setAddress(address);
        order.setState(state);
        order.setShopIcon(shopIon);
        order.setDeliveryPrice(deliveryPrice);

        return order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public AddressCard getAddress() {
        return address;
    }

    public void setAddress(AddressCard address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getShopIon() {
        return shopIon;
    }

    public void setShopIon(String shopIon) {
        this.shopIon = shopIon;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Override
    public String toString() {
        return "OrderCard{" +
                "id='" + id + '\'' +
                ", entity='" + entity + '\'' +
                ", s_name='" + s_name + '\'' +
                ", s_phone='" + s_phone + '\'' +
                ", shopName='" + shopName + '\'' +
                ", createAt=" + createAt +
                ", address=" + address +
                ", state=" + state +
                '}';
    }
}
