package com.wuxiankeneng.factory.model.order;

import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.card.GoodsCard;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by White paper on 2019/9/1
 * Describe :
 */
public class CreateOrderModel implements Serializable {
    private String id;
    private String shopId;
    private List<GoodsCard> goodsCards;
    private AddressCard addressCard;//地址名字电话等信息
    private String remarks;


    public CreateOrderModel(String shopId, List<GoodsCard> goodsCards, AddressCard address) {
        this.id = UUID.randomUUID().toString();
        this.shopId = shopId;
        this.goodsCards = goodsCards;
        this.addressCard = address;
    }

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

    public AddressCard getAddress() {
        return addressCard;
    }

    public void setAddress(AddressCard address) {
        this.addressCard = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
