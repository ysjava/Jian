package com.wuxiankeneng.factory.model.account;

import com.wuxiankeneng.factory.card.StudentCard;

public class AccountRspModel {
    private StudentCard studentCard;
    private String phone;
    // 当前登录成功后获取的Token,可以通过Token获取用户的所有信息
    private String token;
    //表示是否绑定设备的pushId, 服务端推送使用个推推送消息过来需要的id,绑定即代表服务端数据库有存储这个id
    private boolean isBind;


    public StudentCard getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(StudentCard studentCard) {
        this.studentCard = studentCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
