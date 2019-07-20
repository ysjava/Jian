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
        //判断是链接还是id
        String substring = mShopId.substring(0, 4);
        boolean isHttp = substring.equals("http");
        if (isHttp)
            recommend.setType(Recommend.TYPE_URL);
        else
            recommend.setType(Recommend.TYPE_ID);

        return recommend;
    }
}
