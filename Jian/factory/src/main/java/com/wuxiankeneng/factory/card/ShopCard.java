package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;

import java.util.List;
import java.util.UUID;

public class ShopCard {

    private String id;
    private String name;
    private String desc;
    private String img;
    private String sales;
    private List<Goods> recommendGoods;//商店的推荐菜品,限制三个

    public Shop build() {
        Shop shop = new Shop();
        shop.setId(UUID.randomUUID().toString());//生成一个uuid
        shop.setsId(id);
        shop.setName(name);
        shop.setDesc(desc);
        shop.setImg(img);
        shop.setSales(sales);
        shop.setRecommendGoods(recommendGoods);

        return shop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
