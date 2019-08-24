package com.wuxiankeneng.jian.bean.db;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "TB_TESTA")
public class TestDA {
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

    //店铺的推荐菜
    @JoinColumn(name = "daId")
    // 定义为懒加载，默认加载Shop信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TestDB> testDBS = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<TestDB> getTestDBS() {
        return testDBS;
    }

    public void setTestDBS(Set<TestDB> testDBS) {
        this.testDBS = testDBS;
    }
}
