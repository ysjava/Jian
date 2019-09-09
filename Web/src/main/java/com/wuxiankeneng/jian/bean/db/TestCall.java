package com.wuxiankeneng.jian.bean.db;

import com.google.gson.annotations.Expose;

public class TestCall {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
