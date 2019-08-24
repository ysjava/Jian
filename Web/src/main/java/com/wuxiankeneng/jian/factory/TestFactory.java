package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.db.TestDA;
import com.wuxiankeneng.jian.bean.db.TestDB;
import com.wuxiankeneng.jian.utils.Hib;

public class TestFactory {
    public static TestDA findById(String taId) {
        return Hib.query(session -> session.get(TestDA.class, taId));
    }
//    TestDA testDA = (TestDA) session.createQuery("from TestDA where id=:id")
//            .setParameter("id", taId)
//            .uniqueResult();
//            session.load(testDA, testDA.getId());


    public static void create() {
        Hib.queryOnly(session -> {
            TestDA testDA = new TestDA();
            session.save(testDA);
            TestDB testDB1 = new TestDB();
            TestDB testDB2 = new TestDB();
            testDB1.setTestDA(testDA);
            testDB2.setTestDA(testDA);
            session.save(testDB1);
            session.save(testDB2);
        });
    }

    public static TestDA load(TestDA testDA) {
        return Hib.query(session -> {
            session.load(testDA, testDA.getId());
            session.refresh(testDA);
            return testDA;
        });
    }
}
