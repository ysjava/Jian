package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Trader;

public class TraderCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String portrait;

    public TraderCard(Trader trader) {
        this.id = trader.getId();
        this.name = trader.getName();
        this.phone = trader.getPhone();
        this.portrait = trader.getPortrait();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
