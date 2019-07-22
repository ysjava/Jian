package com.wuxiankeneng.factory.model.account;

public class RegisterModel {
    private String phone;
    private String password;
    private String pushId;

    public RegisterModel(String phone, String password, String pushId) {
        this.phone = phone;
        this.password = password;
        this.pushId = pushId;
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
}
