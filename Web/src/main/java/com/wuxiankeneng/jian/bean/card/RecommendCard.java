package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Recommend;

public class RecommendCard {
    @Expose
    private String imgUrl;
    //这儿既可以是商店id也可以是广告url,根据类型来判断
    @Expose
    private String shopIdOrAdvertUrl;
    @Expose
    private int type;

    public RecommendCard(Recommend recommend) {
        this.imgUrl = recommend.getImgUrl();
        this.shopIdOrAdvertUrl = recommend.getShopIdOrAdvertUrl();
        this.type = recommend.getType();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShopIdOrAdvertUrl() {
        return shopIdOrAdvertUrl;
    }

    public void setShopIdOrAdvertUrl(String shopIdOrAdvertUrl) {
        this.shopIdOrAdvertUrl = shopIdOrAdvertUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
