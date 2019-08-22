package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.SimpleGoods;

public class SimpleGoodsCard {
    private String icon;
    private String name;

    public SimpleGoods build() {
        SimpleGoods goods = new SimpleGoods();
        goods.setIcon(icon);
        goods.setName(name);
        return goods;
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
}
