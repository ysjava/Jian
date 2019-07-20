package com.wuxiankeneng.factory.db;

import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import org.litepal.crud.LitePalSupport;

import java.util.List;
import java.util.Objects;

public class Shop extends LitePalSupport
        implements DiffUiDataCallback.UiDataDiffer<Shop> {
    private String id;
    private String sId;
    private String name;
    private String desc;
    private String img;
    private String sales;
    private List<Goods> recommendGoods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public List<Goods> getRecommendGoods() {
        return recommendGoods;
    }

    public void setRecommendGoods(List<Goods> recommendGoods) {
        this.recommendGoods = recommendGoods;
    }

    @Override
    public boolean isSame(Shop old) {
        //对比id即可
        return this == old || Objects.equals(sId, old.sId);
    }

    @Override
    public boolean isUiContentSame(Shop old) {
        return this == old || (
                Objects.equals(name, old.name)
                        && Objects.equals(desc, old.desc)
                        && Objects.equals(img, old.img)
                        && Objects.equals(recommendGoods, old.recommendGoods)
        );
    }
}
