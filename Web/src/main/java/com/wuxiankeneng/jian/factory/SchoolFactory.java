package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.db.Recommend;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.utils.Hib;

import java.util.List;

public class SchoolFactory {
    public static School findById(String schoolId) {
        return Hib.query(session -> session.get(School.class, schoolId));
    }

    public static School findByName(String schoolName) {
        return Hib.query(session -> (School) session
                .createQuery("from School where name=:name")
                .setParameter("name", schoolName)
                .uniqueResult());
    }

    public static School create(String schoolName) {
        return Hib.query(session -> {
            School school = new School();
            school.setName(schoolName);
            session.save(school);
            return school;
        });
    }

    public static List<Recommend> loadRecommend(School school) {
        return Hib.query(session -> {

            @SuppressWarnings("unchecked")
            List<Recommend> recommends = session.createQuery("from Recommend where school=:school")
                    .setParameter("school", school)
                    .setMaxResults(6)
                    .list();
            return recommends;
        });
    }
}
