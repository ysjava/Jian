package com.wuxiankeneng.factory.card;

public class RecommendCard {
    private String mImgUrl;
    private String mShopId;//这儿可作为广告的链接或商店的id

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public String getmShopId() {
        return mShopId;
    }

    public void setmShopId(String mShopId) {
        this.mShopId = mShopId;
    }

    public Recommend buildRecommend() {
        Recommend recommend = new Recommend();
        recommend.setImgPath(mImgUrl);
        recommend.setUrlOrId(mShopId);
        recommend.setType(0);//这儿设置的类型应该对mShopId进行判断,是id还是链接

        return recommend;
    }
}
