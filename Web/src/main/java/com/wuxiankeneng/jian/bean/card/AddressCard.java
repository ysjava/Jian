package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.Address;
import com.wuxiankeneng.jian.bean.db.Student;

public class AddressCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private String phone;

    public AddressCard(Address address) {
        this.id = address.getId();
        this.name = address.getName();
        this.address = address.getAddress();
        this.phone = address.getPhone();
    }

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

    public Address build(Student student){
        Address address = new Address();
        address.setName(name);
        address.setPhone(phone);
        address.setStudent(student);
        address.setAddress(this.address);
        return address;
    }
}
