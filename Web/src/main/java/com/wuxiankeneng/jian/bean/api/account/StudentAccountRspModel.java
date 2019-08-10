package com.wuxiankeneng.jian.bean.api.account;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.card.StudentCard;
import com.wuxiankeneng.jian.bean.db.Student;

public class StudentAccountRspModel {
    @Expose
    private String phone;
    @Expose
    private StudentCard card;
    @Expose
    private String token;
    @Expose
    private boolean isBind;

    public StudentAccountRspModel(Student student) {
        this(student, false);
    }

    public StudentAccountRspModel(Student student, boolean isBind) {

        this.card = new StudentCard(student);
        this.isBind = isBind;
        this.phone = student.getPhone();
        this.token = student.getToken();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StudentCard getCard() {
        return card;
    }

    public void setCard(StudentCard card) {
        this.card = card;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
