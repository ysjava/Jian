package com.wuxiankeneng.jian.bean.db;

import com.wuxiankeneng.jian.bean.api.shop.CreateShopModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "TB_SHOP")
public class Shop {
    public static final int RANGE_DORM = 0;//寝室
    public static final int RANGE_DOWNSTAIRS = 1;//楼下
    public static final int RANGE_NULL = 2;//不能配送
    /*
     * UUID是为了保证id的安全性
     * */
    //这是一个主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生成存储的类型为uuid  generated : 生成
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2,uuid2是常规的uuid  toString
    //  这个uuid2是对生成的id显示得容易观察
    // generator : 生成器
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //禁止自己更新,禁止为空
    private String id;
    //店名
    @Column(unique = true, nullable = false, length = 62)
    private String name;
    //店icon
    @Column(nullable = false)
    private String icon;
    //主页背景图
    @Column(nullable = false)
    private String picture;
    //描述
    @Column
    private String description;
    //公告
    @Column
    private String notice;
    //是否可预订
    @Column(nullable = false)
    private boolean isReserve;
    //配送范围,寝室还是楼下还是不能配送
    @Column(nullable = false)
    private int deliveryRange;
    //月销量
    @Column
    private String sales;
    //配送时间
    @Column(nullable = false)
    private String deliveryDate;
    //是否营业
    @Column(nullable = false)
    private boolean isBusiness;
    // 定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();


    //创建者,一个人可以创建多个店铺
    @JoinColumn(name = "creatorId")
    @ManyToOne(optional = false)
    private Trader creator;
    //提取字段
    @Column(nullable = false, updatable = false, insertable = false)
    private String creatorId;


    //店铺的推荐菜
    @JoinColumn(name = "recommendShopId")
    // 定义为懒加载，默认加载Shop信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Goods> recommendGoods = new HashSet<>();

    //店铺的所有菜
    @JoinColumn(name = "shopId")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Goods> allGoods = new HashSet<>();

    public Shop() {
    }

    public Shop(Trader creator, CreateShopModel model) {
        this.creator = creator;
        this.name = model.getName();
        this.icon = model.getIcon();
        this.picture = model.getPicture();
        this.deliveryDate = model.getDeliveryDate();
        this.deliveryRange = model.getDeliveryRange();
        this.isBusiness = true;
        this.isReserve= true;
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

    public int getDeliveryRange() {
        return deliveryRange;
    }

    public void setDeliveryRange(int deliveryRange) {
        this.deliveryRange = deliveryRange;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Set<Goods> getRecommendGoods() {
        return recommendGoods;
    }

    public void setRecommendGoods(Set<Goods> recommendGoods) {
        this.recommendGoods = recommendGoods;
    }

    public Set<Goods> getAllGoods() {
        return allGoods;
    }

    public void setAllGoods(Set<Goods> allGoods) {
        this.allGoods = allGoods;
    }

    public Trader getCreator() {
        return creator;
    }

    public void setCreator(Trader creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
