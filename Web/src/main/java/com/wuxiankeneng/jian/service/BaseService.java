package com.wuxiankeneng.jian.service;

import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.bean.db.Trader;
import com.wuxiankeneng.jian.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseService {
    // 添加一个上下文注解，该注解会给securityContext赋值
    // 具体的值为我们的拦截器中所返回的SecurityContext
    @Context
    protected SecurityContext securityContext;

    /**
     * 从上下文中直接获取当前用户的信息 : 学生或者商家
     *
     * @return User :接口, 学生和商家都实现了它
     */
    protected User getSelf() {
        if (getStudentSelf() == null) {
            return getTraderSelf();
        }
        return getStudentSelf();
    }


    /**
     * 从上下文中直接获取自己(学生)的信息
     *
     * @return Student
     */
    private Student getStudentSelf() {
        return (Student) securityContext.getUserPrincipal();
    }

    /**
     * 从上下文中直接获取自己(商家)的信息
     *
     * @return Trader
     */
    private Trader getTraderSelf() {
        return (Trader) securityContext.getUserPrincipal();
    }
}
