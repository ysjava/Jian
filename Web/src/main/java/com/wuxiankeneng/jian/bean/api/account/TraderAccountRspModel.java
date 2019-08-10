package com.wuxiankeneng.jian.bean.api.account;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.card.TraderCard;
import com.wuxiankeneng.jian.bean.db.Trader;

public class TraderAccountRspModel {
    @Expose
    private String phone;
    @Expose
    private TraderCard card;
    @Expose
    private String token;
    @Expose
    private boolean isBind;

    public TraderAccountRspModel(Trader trader) {
        this(trader, false);
    }

    public TraderAccountRspModel(Trader trader, boolean isBind) {

        this.card = new TraderCard(trader);
        this.phone = trader.getPhone();
        this.isBind = isBind;
        this.token = trader.getToken();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TraderCard getCard() {
        return card;
    }

    public void setCard(TraderCard card) {
        this.card = card;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
