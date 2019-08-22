package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;

public class SimpleGoodsCard {
    @Expose
    private String icon;
    @Expose
    private String name;

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


}
