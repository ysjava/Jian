package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Goods;
import com.wuxiankeneng.factory.db.Shop;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShopCard {

    private String id;
    private String name;
    private String desc;
    private String img;
    private String sales;
    private String icon;
    private int deliveryDate;//配送大约时间
    private String notice;//公告
    private int range;//配送范围
    private boolean isReserve;//是否预约
    private boolean isBusiness;//是否营业
    private List<Goods> recommendGoods;//商店的推荐菜品,限制三个
    private List<Goods> allGoods;//所有商品

    public Shop build() {
        Shop shop = new Shop();
        shop.setId(UUID.randomUUID().toString());//生成一个uuid
        shop.setsId(id);
        shop.setName(name);
        shop.setDesc(desc);
        shop.setImg(img);
        shop.setSales(sales);
        shop.setRecommendGoods(recommendGoods);
        shop.setIcon(icon);
        shop.setDeliveryDate(deliveryDate);
        shop.setDeliveryRange(range);
        shop.setNotice(notice);
        shop.setReserve(isReserve);
        shop.setBusiness(isBusiness);
        shop.setAllGoods(allGoods);
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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(int deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isReserve() {
        return isReserve;
    }

    public void setReserve(boolean reserve) {
        isReserve = reserve;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public List<Goods> getAllGoods() {
        return allGoods;
    }

    public void setAllGoods(List<Goods> allGoods) {
        this.allGoods = allGoods;
    }
}
