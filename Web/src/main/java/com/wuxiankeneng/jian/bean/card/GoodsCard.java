package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Goods;

import javax.persistence.Column;

public class GoodsCard {
    @Expose
    private String id;
    //商品名
    @Expose
    private String name;
    //商品icon
    @Expose
    private String icon;
    //商品描述
    @Expose
    private String description;
    //月销量
    @Expose
    private int monthlySales;
    //商品类型
    @Expose
    private String type;
    //类型id
    @Expose
    private int typeId;
    //价格
    @Expose
    private String price;
    //原价格
    @Expose
    private String originalPrice;
    //学生选购数量
    @Expose
    private int count;

    public GoodsCard(Goods goods) {
        this.id = goods.getId();
        this.name = goods.getName();
        this.icon = goods.getIcon();
        this.description = goods.getDescription();
        this.monthlySales = goods.getMonthlySales();
        this.type = goods.getType();
        this.typeId = goods.getTypeId();
        this.price = goods.getPrice();
        this.originalPrice = goods.getOriginalPrice();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
}
