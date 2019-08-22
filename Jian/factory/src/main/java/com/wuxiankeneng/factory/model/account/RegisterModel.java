package com.wuxiankeneng.factory.model.account;

public class RegisterModel {
    private String phone;
    private String password;
    private String pushId;
    private String name;
    private String schoolId;

    public RegisterModel(String phone, String password, String pushId,String name,String schoolId) {
        this.phone = phone;
        this.password = password;
        this.pushId = pushId;
        this.name = name;
        this.schoolId = schoolId;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
