package com.wuxiankeneng.factory.db;

import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by White paper on 2019/9/6
 * Describe :
 */
public class Order implements DiffUiDataCallback.UiDataDiffer<Order>,Serializable{
    //待处理
    public static final int STATE_PROCESSING = 0;
    //以接受
    public static final int STATE_ACCEPT = 1;
    //以完成
    public static final int STATE_DONE = 100;


    private String oId;
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
    //店图标
    private String shopIcon;
    //配送费
    private String deliveryPrice;


    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
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

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Override
    public boolean isSame(Order old) {
        return this == old || Objects.equals(oId, old.oId);
    }

    @Override
    public boolean isUiContentSame(Order old) {
        return Objects.equals(entity, old.entity)
                && Objects.equals(shopName, old.shopName)
                && Objects.equals(shopIcon, old.shopIcon)
                && Objects.equals(state, old.state);
    }
}
