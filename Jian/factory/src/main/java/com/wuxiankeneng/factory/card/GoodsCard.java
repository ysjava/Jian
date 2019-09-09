package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Goods;

import java.io.Serializable;

public class GoodsCard implements Serializable {
    private String id;
    //商品名
    private String name;
    //商品icon
    private String icon;
    //商品描述
    private String description;
    //月销量
    private int monthlySales;
    //商品类型
    private String type;
    //类型id
    private int typeId;
    //价格
    private String price;
    //原价格
    private String originalPrice;
    //学生选购数量
    private int count;
    //包装费
    private String packingPrice;

    public Goods build() {
        Goods goods = new Goods();
        goods.setsId(id);
        goods.setName(name);
        goods.setIcon(icon);
        goods.setDesc(description);
        goods.setSales(monthlySales);
        goods.setType(type);
        goods.setTypeId(typeId);
        goods.setPrice(price);
        goods.setOriginalPrice(originalPrice);
        goods.setPackingPrice(packingPrice);

        return goods;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPackingPrice() {
        return packingPrice;
    }

    public void setPackingPrice(String packingPrice) {
        this.packingPrice = packingPrice;
    }
}
