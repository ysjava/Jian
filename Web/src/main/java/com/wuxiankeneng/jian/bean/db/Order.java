package com.wuxiankeneng.jian.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

//订单表
@Entity
@Table(name = "TB_ORDER")
public class Order {
    //待处理
    public static final int STATE_PROCESSING = 0;
    //以接受
    public static final int STATE_ACCEPT = 1;
    //以完成
    public static final int STATE_DONE = 100;

    /*
     * UUID是为了保证id的安全性
     * */
    //这是一个主键
    @Id
    @PrimaryKeyJoinColumn
    //把uuid的生成器定义为uuid2,uuid2是常规的uuid  toString
    //  这个uuid2是对生成的id显示得容易观察
    // generator : 生成器
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //禁止自己更新,禁止为空
    private String id;

    //订单的商品信息,以lob形式储存
    @Lob
    @Column(nullable = false, columnDefinition = "BLOB")
    private String entity;

    //订单的发起者
    @JoinColumn(name = "studentId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    private Student student;
    @Column(updatable = false, nullable = false, insertable = false)
    private String studentId;

    //订单所属商店
    @JoinColumn(name = "shopId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Shop shop;
    @Column(updatable = false, nullable = false, insertable = false)
    private String shopId;

    //订单创建时间
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();
    //配送地址
    @Column(nullable = false)
    private String address;
    //订单状态
    @Column(nullable = false)
    private int state;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
