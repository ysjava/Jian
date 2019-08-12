package com.wuxiankeneng.jian.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "TB_GOODS")
public class Goods {
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

    //定义的店铺推荐菜
    //不是必须有,因为这道菜可能不是店铺推荐的
    @JoinColumn(name = "recommendShopId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Shop shopRecommend;
    //把这个列提取到我们的Model中,不允许更新，插入
    @Column(insertable = false, updatable = false)
    private String recommendShopId;


    //定义的菜品所属店铺
    //必须有
    @JoinColumn(name = "shopId")
    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    private Shop shop;
    //把这个列提取到我们的Model中,不允许更新，插入
    @Column(nullable = false, insertable = false, updatable = false)
    private String shopId;

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

    public Shop getShopRecommend() {
        return shopRecommend;
    }

    public void setShopRecommend(Shop shopRecommend) {
        this.shopRecommend = shopRecommend;
    }

    public String getRecommendShopId() {
        return recommendShopId;
    }

    public void setRecommendShopId(String recommendShopId) {
        this.recommendShopId = recommendShopId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
