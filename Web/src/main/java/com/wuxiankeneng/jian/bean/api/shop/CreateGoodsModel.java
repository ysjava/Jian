package com.wuxiankeneng.jian.bean.api.shop;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

//名字 图标 类型 类型id 现价 原价 描述
public class CreateGoodsModel {
    @Expose
    private String name;
    @Expose
    private String icon;
    @Expose
    private String description;
    //类型
    @Expose
    private String type;
    //类型id
    @Expose
    private int typeId;
    //现价
    @Expose
    private String price;
    //原价
    @Expose
    private String originalPrice;

    public static boolean check(CreateGoodsModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.name)
                && !Strings.isNullOrEmpty(model.icon)
                && !Strings.isNullOrEmpty(model.type)
                && !Strings.isNullOrEmpty(String.valueOf(model.typeId))
                && !Strings.isNullOrEmpty(model.price)
                && !Strings.isNullOrEmpty(model.originalPrice);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }
}
