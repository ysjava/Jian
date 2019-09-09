package com.wuxiankeneng.factory.card;

import java.io.Serializable;

/**
 * Created by White paper on 2019/9/4
 * Describe : 地址信息
 */
public class AddressCard implements Serializable {
    private String id;
    private String name;
    private String address;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
