package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShopCard {

    private String id;
    private String name;
    private String description;
    private String picture;
    private String sales;
    private String icon;
    private String deliveryDate;//配送大约时间
    private String deliveryPrice;//配送费
    private String minimumPrice;//最低起送金额
    private String notice;//公告
    private int deliveryRange;//配送范围
    private boolean isReserve;//是否预约
    private boolean isBusiness;//是否营业
    private List<GoodsCard> goodsList;//所有商品
//    private Date createAt;


    public Shop build() {
        Shop shop = new Shop();

        List<Goods> goodsArrayList = new ArrayList<>();
        for (GoodsCard goodsCard : goodsList) {
            goodsArrayList.add(goodsCard.build());
        }

        shop.setsId(id);
        shop.setName(name);
        shop.setDesc(description);
        shop.setImg(picture);
        shop.setSales(sales);
        shop.setIcon(icon);
        shop.setDeliveryDate(deliveryDate);
        shop.setDeliveryRange(deliveryRange);
        shop.setNotice(notice);
        shop.setReserve(isReserve);
        shop.setBusiness(isBusiness);
        shop.setDeliveryPrice(deliveryPrice);
        shop.setMinimumPrice(minimumPrice);
        shop.setAllGoods(goodsArrayList);
        return shop;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }



    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getRange() {
        return deliveryRange;
    }

    public void setRange(int range) {
        this.deliveryRange = range;
    }

    public boolean isReserve() {
        return isReserve;
    }

    public void setReserve(boolean reserve) {
        isReserve = reserve;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public List<GoodsCard> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsCard> goodsList) {
        this.goodsList = goodsList;
    }
}
