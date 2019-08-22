package com.wuxiankeneng.factory.card;

public class Recommend {
    public static final int TYPE_ID = 0;
    public static final int TYPE_URL = 1;
    private String imgUrl;
    private String shopIdOrAdvertUrl;
    private int type;

    public Recommend() {
    }

    public Recommend(String imgUrl, String shopIdOrAdvertUrl, int type) {
        this.imgUrl = imgUrl;
        this.shopIdOrAdvertUrl = shopIdOrAdvertUrl;
        this.type = type;
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
