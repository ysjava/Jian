package com.wuxiankeneng.factory.card;

public class RecommendCard {
    private String imgUrl;
    private String shopIdOrAdvertUrl;//这儿可作为广告的链接或商店的id
    private int type;

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

    public Recommend buildRecommend() {
        return new Recommend(imgUrl,shopIdOrAdvertUrl,type);
    }
}
