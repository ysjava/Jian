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
}
