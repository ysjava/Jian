package com.wuxiankeneng.jian.bean.api.shop;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

public class CreateShopModel {

    @Expose
    private String  name;//店名
    @Expose
    private String icon;//店图标
    @Expose
    private String picture;//店主背景图
    @Expose
    private int deliveryRange;//配送范围
    @Expose
    private String deliveryDate;//配送时间
    @Expose
    private String schoolId;//该店铺所属的学校id

    public static boolean check(CreateShopModel model) {
        return model!=null
                &&!Strings.isNullOrEmpty(model.name)
                &&!Strings.isNullOrEmpty(model.icon)
                &&!Strings.isNullOrEmpty(model.picture)
                &&!Strings.isNullOrEmpty(model.schoolId)
                &&(model.deliveryRange==0||model.deliveryRange==1||model.deliveryRange==2)
                &&!Strings.isNullOrEmpty(model.deliveryDate);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getDeliveryRange() {
        return deliveryRange;
    }

    public void setDeliveryRange(int deliveryRange) {
        this.deliveryRange = deliveryRange;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
