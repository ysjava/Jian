package com.wuxiankeneng.jian.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.security.Principal;

@Entity
@Table(name = "TB_ADMIN")
public class Admin implements Principal {
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

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return null;
    }
}
