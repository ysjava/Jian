package com.wuxiankeneng.jian.bean.api.order;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.card.GoodsCard;

import java.util.List;

public class CreateOrderModel {
    // ID从客户端生产，一个UUID
    @Expose
    private String id;
    //店铺id
    @Expose
    private String shopId;
    //购买的商品集合
    @Expose
    private List<GoodsCard> goodsCards;
    //配送地址
    @Expose
    private String address;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<GoodsCard> getGoodsCards() {
        return goodsCards;
    }

    public void setGoodsCards(List<GoodsCard> goodsCards) {
        this.goodsCards = goodsCards;
    }

    public static boolean check(CreateOrderModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.getId())
                && !Strings.isNullOrEmpty(model.getShopId())
                && !Strings.isNullOrEmpty(model.getAddress())
                && !(model.getGoodsCards() == null || model.getGoodsCards().size() == 0);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
