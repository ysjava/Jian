package com.wuxiankeneng.jian.bean.api.shop;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.card.GoodsCard;

import java.util.List;

//商品添加的返回model  返回了成功创建与创建失败的商品
public class GoodsCreateResponseModel {
    //创建成功商品的集合
    @Expose
    private List<GoodsCard> createSuccessList;
    //创建失败的商品的名字
    @Expose
    private List<String> createFailList;

    public GoodsCreateResponseModel(List<GoodsCard> createSuccessList, List<String> createFailList) {
        this.createSuccessList = createSuccessList;
        this.createFailList = createFailList;
    }

    public List<GoodsCard> getCreateSuccessList() {
        return createSuccessList;
    }

    public void setCreateSuccessList(List<GoodsCard> createSuccessList) {
        this.createSuccessList = createSuccessList;
    }

    public List<String> getCreateFailList() {
        return createFailList;
    }

    public void setCreateFailList(List<String> createFailList) {
        this.createFailList = createFailList;
    }
}
