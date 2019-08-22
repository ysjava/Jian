package com.wuxiankeneng.factory.db;

import org.litepal.crud.LitePalSupport;

public class SimpleGoods extends LitePalSupport {
    private String icon;
    private String name;

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
