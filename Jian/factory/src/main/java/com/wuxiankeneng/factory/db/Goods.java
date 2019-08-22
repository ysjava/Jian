package com.wuxiankeneng.factory.db;

import java.io.Serializable;

//商品表
public class Goods implements Serializable {
    private String id;
    private String sId;
    private String name;
    private String desc;
    private String img;
    private int count;
    private String sales;
    private int dailySales;
    private String type;
    private int typeId;
    private double price;
    private Shop shop;
    public Goods() {
    }

    public Goods(String id, String name, String img, String sales, String type, int typeId, double price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.sales = sales;
        this.type = type;
        this.typeId = typeId;
        this.price = price;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
