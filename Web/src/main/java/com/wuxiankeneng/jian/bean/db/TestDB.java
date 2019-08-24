package com.wuxiankeneng.jian.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "TB_TESTB")
public class TestDB {
    /*
     * UUID是为了保证id的安全性
     *
     **/
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

    //定义的菜品所属店铺
    //必须有
    @JoinColumn(name = "daId")
    @ManyToOne(optional = false)
    private TestDA testDA;
    //把这个列提取到我们的Model中,不允许更新，插入
    @Column(insertable = false, updatable = false)
    private String daId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestDA getTestDA() {
        return testDA;
    }

    public void setTestDA(TestDA testDA) {
        this.testDA = testDA;
    }

    public String getDaId() {
        return daId;
    }

    public void setDaId(String daId) {
        this.daId = daId;
    }
}
