package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;

public class DailySalesGoodsCard {
//    id,g_name,dailySales,icon,price,shop
    @Expose
    private String id;
    @Expose
    private String g_name;
    @Expose
    private int dailySales;
    @Expose
    private String icon;
    @Expose
    private String price;
    @Expose
    private String shopName;

    public DailySalesGoodsCard(String id, String g_name, int dailySales, String icon, String price, String shopName) {
        this.id = id;
        this.g_name = g_name;
        this.dailySales = dailySales;
        this.icon = icon;
        this.price = price;
        this.shopName = shopName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public int getDailySales() {
        return dailySales;
    }

    public void setDailySales(int dailySales) {
        this.dailySales = dailySales;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
