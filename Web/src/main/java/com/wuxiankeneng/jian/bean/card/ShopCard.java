package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.Shop;
import com.wuxiankeneng.jian.factory.GoodsFactory;
import com.wuxiankeneng.jian.factory.ShopFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String icon;
    @Expose
    private String picture;
    @Expose
    private String description;
    @Expose
    private String notice;
    @Expose
    private boolean isReserve;
    @Expose
    private int deliveryRange;
    @Expose
    private String sales;
    @Expose
    private String deliveryDate;
    @Expose
    private boolean isBusiness;
//    @Expose
//    private LocalDateTime createAt;
    @Expose
    private List<GoodsCard> goodsList;
    //配送费
    @Expose
    private String deliveryPrice;
    //最低起送金额
    @Expose
    private String minimumPrice;

    public ShopCard(Shop shop, boolean isLoad) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.icon = shop.getIcon();
        this.picture = shop.getPicture();
        this.description = shop.getDescription();
        this.notice = shop.getNotice();
        this.isReserve = shop.isReserve();
        this.deliveryRange = shop.getDeliveryRange();
        this.sales = shop.getSales();
        this.deliveryDate = shop.getDeliveryDate();
        this.isBusiness = shop.isBusiness();
        this.deliveryPrice = shop.getDeliveryPrice();
        this.minimumPrice = shop.getMinimumPrice();
//        this.createAt = shop.getCreateAt();

        if (isLoad)
            this.goodsList = ShopFactory.getAllGoods(shop).stream()
                    .map(GoodsCard::new)
                    .collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isReserve() {
        return isReserve;
    }

    public void setReserve(boolean reserve) {
        isReserve = reserve;
    }

    public int getDeliveryRange() {
        return deliveryRange;
    }

    public void setDeliveryRange(int deliveryRange) {
        this.deliveryRange = deliveryRange;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

//    public LocalDateTime getCreateAt() {
//        return createAt;
//    }
//
//    public void setCreateAt(LocalDateTime createAt) {
//        this.createAt = createAt;
//    }

    public List<GoodsCard> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsCard> goodsList) {
        this.goodsList = goodsList;
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
}
