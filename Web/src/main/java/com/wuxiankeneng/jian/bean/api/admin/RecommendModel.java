package com.wuxiankeneng.jian.bean.api.admin;

import com.google.gson.annotations.Expose;

public class RecommendModel {
    @Expose
    private String imgUrl;
    @Expose
    private String shopIdOrAdvertUrl;
    @Expose
    private String schoolId;
    @Expose
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
