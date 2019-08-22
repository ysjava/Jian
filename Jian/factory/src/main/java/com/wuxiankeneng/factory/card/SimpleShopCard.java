package com.wuxiankeneng.factory.card;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.SimpleGoods;
import com.wuxiankeneng.factory.db.SimpleShop;

import java.util.ArrayList;
import java.util.List;

//简单的shop卡片
public class SimpleShopCard {
    private String name;
    private String icon;
    private String sId;
    private boolean isBusiness;
    private List<SimpleGoodsCard> simpleGoodsCards;
    private String desc;

    //配送时间
    private String deliveryDate;
    //配送费
    private String deliveryPrice;
    //起送金额
    private String minimumPrice;
    //销量
    private String sales;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public List<SimpleGoodsCard> getSimpleGoodsCards() {
        return simpleGoodsCards;
    }

    public void setSimpleGoodsCards(List<SimpleGoodsCard> simpleGoodsCards) {
        this.simpleGoodsCards = simpleGoodsCards;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
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

    public SimpleShop build() {
        SimpleShop simpleShop = new SimpleShop();

        List<SimpleGoods> simpleGoods = new ArrayList<>();
        for (SimpleGoodsCard simpleGoodsCard : simpleGoodsCards) {
            simpleGoods.add(simpleGoodsCard.build());
        }

        simpleShop.setsId(sId);
        simpleShop.setIcon(icon);
        simpleShop.setName(name);
        simpleShop.setDesc(desc == null ? "" : desc);
        simpleShop.setBusiness(isBusiness);
        simpleShop.setSimpleGoods(simpleGoods);

        simpleShop.setDeliveryDate(deliveryDate);
        simpleShop.setDeliveryPrice(deliveryPrice);
        simpleShop.setMinimumPrice(minimumPrice);

        simpleShop.setSales(sales);

        return simpleShop;
    }
}
