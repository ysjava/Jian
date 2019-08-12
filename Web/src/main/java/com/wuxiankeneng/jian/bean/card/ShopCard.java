package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Shop;

import java.time.LocalDateTime;

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
    @Expose
    private LocalDateTime createAt ;

    public ShopCard(Shop shop) {
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
        this.createAt = shop.getCreateAt();
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
