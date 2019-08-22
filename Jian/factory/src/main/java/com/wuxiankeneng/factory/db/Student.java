package com.wuxiankeneng.factory.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Student extends LitePalSupport {
    private int id;
    @Column(unique = true)
    private String sId;
    private String name;
    private String phone;
//    private Date modifyAt;
//    private String address;
    private String portrait;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public Date getModifyAt() {
//        return modifyAt;
//    }
//
//    public void setModifyAt(Date modifyAt) {
//        this.modifyAt = modifyAt;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }


    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

}
