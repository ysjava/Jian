package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;

public class SearchShopCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String desc;

    public SearchShopCard(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
