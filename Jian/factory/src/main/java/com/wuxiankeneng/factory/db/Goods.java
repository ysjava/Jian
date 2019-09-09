package com.wuxiankeneng.factory.db;

import com.wuxiankeneng.factory.card.GoodsCard;

import java.io.Serializable;

//商品表
public class Goods implements Serializable {
    private String id;
    private String sId;
    private String name;
    private String desc;
    private String icon;
    private int count;//选择数量的多少  这个只在购买商品时才会用到,并不会保存这个数据
    private int sales;
    private int dailySales;
    private String type;
    private int typeId;
    private String price;
    private String originalPrice;
    private Shop shop;
    //包装费
    private String packingPrice;

    public Goods() {
    }

    public Goods(String id, String name, String icon, int sales, String type, int typeId, String price,String originalPrice) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.sales = sales;
        this.type = type;
        this.typeId = typeId;
        this.price = price;
        this.originalPrice = originalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPackingPrice() {
        return packingPrice;
    }

    public void setPackingPrice(String packingPrice) {
        this.packingPrice = packingPrice;
    }

    //转换成卡片
    public GoodsCard buildCard() {
        GoodsCard goodsCard = new GoodsCard();
        goodsCard.setId(sId);
        goodsCard.setName(name);
        goodsCard.setIcon(icon);
        goodsCard.setDescription(desc);
        goodsCard.setMonthlySales(sales);
        goodsCard.setType(type);
        goodsCard.setTypeId(typeId);
        goodsCard.setPrice(price);
        goodsCard.setOriginalPrice(originalPrice);
        goodsCard.setCount(count);
        goodsCard.setPackingPrice(packingPrice);

        return goodsCard;
    }
}
