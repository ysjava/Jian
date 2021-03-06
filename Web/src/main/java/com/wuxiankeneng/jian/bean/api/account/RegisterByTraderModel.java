package com.wuxiankeneng.jian.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

public class RegisterByTraderModel {
    @Expose
    private String phone;
    @Expose
    private String password;
    @Expose
    private String pushId;
    @Expose
    private String name;

    public static boolean check(RegisterByTraderModel model) {
        return model!=null
                &&!Strings.isNullOrEmpty(model.phone)
                &&!Strings.isNullOrEmpty(model.password)
                &&!Strings.isNullOrEmpty(model.name);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
