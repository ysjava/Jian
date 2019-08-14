package com.wuxiankeneng.jian.bean.db;

import com.wuxiankeneng.jian.bean.api.shop.CreateGoodsModel;
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
    //商品名
    @Column(nullable = false)
    private String g_name;
    //商品icon
    @Column(nullable = false)
    private String icon;
    //商品描述
    @Column
    private String description;
    //
//    private int count;
    //月销量
    @Column
    private int monthlySales;
    //商品类型
    @Column(nullable = false)
    private String type;
    //类型id
    @Column(nullable = false)
    private int typeId;
    //价格
    @Column(nullable = false)
    private String price;
    //原价格
    @Column(nullable = false)
    private String originalPrice;

    //定义的店铺推荐菜
    //不是必须有,因为这道菜可能不是店铺推荐的
    @JoinColumn(name = "recommendShopId")
    @ManyToOne
    private Shop shopRecommend;
    //把这个列提取到我们的Model中,不允许更新，插入
    @Column(insertable = false, updatable = false)
    private String recommendShopId;


    //定义的菜品所属店铺
    //必须有
    @JoinColumn(name = "shopId")
    @ManyToOne(optional = false)
    private Shop shop;
    //把这个列提取到我们的Model中,不允许更新，插入
    @Column(nullable = false, insertable = false, updatable = false)
    private String shopId;

    public Goods() {
    }

    public Goods(Shop shop, CreateGoodsModel model) {
        this.shop = shop;
        this.g_name = model.getName();
        this.icon = model.getIcon();
        this.description = model.getDescription() == null ? "" : model.getDescription();
        this.type = model.getType();
        this.typeId = model.getTypeId();
        this.price = model.getPrice();
        this.originalPrice = model.getOriginalPrice();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return g_name;
    }

    public void setName(String name) {
        this.g_name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
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
