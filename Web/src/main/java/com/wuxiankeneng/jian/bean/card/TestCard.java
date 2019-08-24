package com.wuxiankeneng.jian.bean.card;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.db.TestDA;
import com.wuxiankeneng.jian.bean.db.TestDB;

import java.util.ArrayList;
import java.util.List;

public class TestCard {
    @Expose
    private List<TestDB> dbList;

    public TestCard(TestDA da) {
        this.dbList = new ArrayList<>(da.getTestDBS());
    }

    public List<TestDB> getDbList() {
        return dbList;
    }

    public void setDbList(List<TestDB> dbList) {
        this.dbList = dbList;
    }
}
