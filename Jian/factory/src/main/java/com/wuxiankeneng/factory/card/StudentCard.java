package com.wuxiankeneng.factory.card;

import com.wuxiankeneng.factory.db.Student;

import java.util.Date;

public class StudentCard {

    private String id;
    private String name;
    private String phone;
    //    private Date modifyAt;
    private String portrait;


    public Student build() {
        Student student = new Student();
        student.setsId(id);
        student.setName(name);
        student.setPhone(phone);
//        student.setModifyAt(modifyAt);
        student.setPortrait(portrait);

        return student;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
