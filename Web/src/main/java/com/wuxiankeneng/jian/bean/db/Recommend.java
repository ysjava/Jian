package com.wuxiankeneng.jian.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "TB_RECOMMEND")
public class Recommend {
    public static final int TYPE_SHOP_ID = 0;
    public static final int TYPE_URL = 1;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schoolId")
    private School school;
    @Column(nullable = false, insertable = false, updatable = false)
    private String schoolId;

    @Column
    private String imgUrl;
    @Column
    private String shopIdOrAdvertUrl;
    @Column
    private int type;

    public Recommend(School school, String imgUrl, String shopIdOrAdvertUrl, int type) {
        this.school = school;
        this.imgUrl = imgUrl;
        this.shopIdOrAdvertUrl = shopIdOrAdvertUrl;
        this.type = type;
    }

    public Recommend() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getShopIdOrAdvertUrl() {
        return shopIdOrAdvertUrl;
    }

    public void setShopIdOrAdvertUrl(String shopIdOrAdvertUrl) {
        this.shopIdOrAdvertUrl = shopIdOrAdvertUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
