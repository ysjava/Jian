package com.wuxiankeneng.jian.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

public class RegisterByStudentModel {
    @Expose
    private String phone;
    @Expose
    private String password;
    @Expose
    private String pushId;
    @Expose
    private String name;
    @Expose
    private String schoolName;

    public static boolean check(RegisterByStudentModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.getPhone())
                && !Strings.isNullOrEmpty(model.getPassword())
                && !Strings.isNullOrEmpty(model.getName());
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
