package com.wuxiankeneng.factory.db;

import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Shop extends LitePalSupport
        implements DiffUiDataCallback.UiDataDiffer<Shop> {
    public static final int RANGE_DORM = 0;//寝室
    public static final int RANGE_DOWNSTAIRS = 1;//楼下
    public static final int RANGE_NULL = 2;//不能配送

    public static final int TYPE_FAST_FOOD = 10;//快餐
    public static final int TYPE_DRINK = 11;//饮品
    public static final int TYPE_NOODLE = 12;//面食
    public static final int TYPE_STIR_FRY = 13;//炒菜
    public static final int TYPE_MULTIPLE = 14;//综合
    public static final int TYPE_FRUITS = 15;//水果
    public static final int TYPE_SUPERMARKET = 16;//超市
    public static final int TYPE_HOT = 17;//热销

    private int id;
    @Column(unique = true)
    private String sId;
    private String name;
    private String desc;
    private String img;//背景图
    private String icon;//店铺图标
    private boolean isReserve;//是否可预订
    private int deliveryRange;//配送范围,寝室还是楼下还是不能配送
    private String deliveryPrice;//配送费
    private String minimumPrice;//最低起送金额
    private String sales;//月销量
    private String deliveryDate;//配送时间
    private String notice;//公告
    private List<Goods> recommendGoods;
    private List<Goods> allGoods;
    private boolean isBusiness;//是否营业


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public List<Goods> getRecommendGoods() {
        return recommendGoods;
    }

    public void setRecommendGoods(List<Goods> recommendGoods) {
        this.recommendGoods = recommendGoods;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isReserve() {
        return isReserve;
    }

    public void setReserve(boolean reserve) {
        isReserve = reserve;
    }

    public List<Goods> getAllGoods() {
        return allGoods;
    }

    public void setAllGoods(List<Goods> allGoods) {
        this.allGoods = allGoods;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
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
                        && Objects.equals(icon, old.icon)
                        && Objects.equals(deliveryPrice, old.deliveryPrice)
                        && Objects.equals(minimumPrice, old.minimumPrice)
        );
    }
}
