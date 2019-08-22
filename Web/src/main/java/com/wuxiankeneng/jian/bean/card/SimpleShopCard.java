package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.factory.ShopFactory;

import java.util.List;

public class SimpleShopCard {


    @Expose
    private String sId;
    @Expose
    private String name;
    @Expose
    private String icon;
    @Expose
    private boolean isBusiness;
    @Expose
    private List<SimpleGoodsCard> simpleGoodsCards;
    @Expose
    private String desc;
    @Expose
    private String deliveryDate;
    @Expose
    private String deliveryPrice;
    @Expose
    private String minimumPrice;
    @Expose
    private String sales;


    public SimpleShopCard(String sId, String name, String icon, boolean isBusiness, String desc,
                          String deliveryDate, String deliveryPrice, String minimumPrice, String sales) {
        this.simpleGoodsCards = ShopFactory.getRecommendsById(sId);
        this.sId = sId;
        this.desc = desc;
        this.name = name;
        this.icon = icon;
        this.isBusiness = isBusiness;
        this.deliveryDate = deliveryDate == null ? "45" : deliveryDate;
        this.deliveryPrice = deliveryPrice == null ? "0" : deliveryPrice;
        this.minimumPrice = minimumPrice == null ? "0" : minimumPrice;
        this.sales = sales == null ? "0" : sales;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public List<SimpleGoodsCard> getSimpleGoodsCards() {
        return simpleGoodsCards;
    }

    public void setSimpleGoodsCards(List<SimpleGoodsCard> simpleGoodsCards) {
        this.simpleGoodsCards = simpleGoodsCards;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
