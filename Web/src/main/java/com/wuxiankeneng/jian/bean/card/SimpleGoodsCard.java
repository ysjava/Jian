package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;

public class SimpleGoodsCard {
    @Expose
    private String icon;
    @Expose
    private String name;
    @Expose
    private int sales;
    @Expose
    private String price;

    public SimpleGoodsCard(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
