package com.wuxiankeneng.factory.db;



import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by White paper on 2019/6/14
 * Describe :
 */
@Table(database = AppDatabase.class)
public class Student extends BaseModel {
// 主键
    @PrimaryKey
    private String stuId;
    @Column
    private String stuName;
    @Column
    private String stuPhone;
    @Column
    private String stuPortrait;
    @Column
    private int sex = 0;
    @Column
    private String stuAddress;//地址
    // 时间字段
    @Column
    private Date modifyAt;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getStuPortrait() {
        return stuPortrait;
    }

    public void setStuPortrait(String stuPortrait) {
        this.stuPortrait = stuPortrait;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getStuAddress() {
        return stuAddress;
    }

    public void setStuAddress(String stuAddress) {
        this.stuAddress = stuAddress;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
}
