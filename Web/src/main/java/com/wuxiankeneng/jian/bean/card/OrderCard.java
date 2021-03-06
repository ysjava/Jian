package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Order;

import java.time.LocalDateTime;

public class OrderCard {
    @Expose
    private String id;
    @Expose
    private String entity;
    //学生名字
    @Expose
    private String s_name;
    //学生手机(收货电话
    @Expose
    private String s_phone;
    //商店名字
    @Expose
    private String shopName;
    //订单创建时间
    @Expose
    private LocalDateTime createAt;
    //配送信息
    @Expose
    private AddressCard address;
    //订单状态
    @Expose
    private int state;
    @Expose
    private String shopIon;
    //配送费
    @Expose
    private String deliveryPrice;

    public OrderCard(Order order) {
        this.id = order.getId();
        this.entity = order.getEntity();
        this.s_name = order.getStudent().getName();
        this.s_phone = order.getStudent().getPhone();
        this.shopName = order.getShop().getName();
        this.createAt = order.getCreateAt();
        this.address = new AddressCard(order.getAddress());
        this.state = order.getState();
        this.shopIon = order.getShop().getIcon();
        this.deliveryPrice = order.getDeliveryPrice();
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
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
}
