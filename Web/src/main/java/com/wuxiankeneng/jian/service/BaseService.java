package com.wuxiankeneng.jian.service;

import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.bean.db.Trader;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseService {
    // 添加一个上下文注解，该注解会给securityContext赋值
    // 具体的值为我们的拦截器中所返回的SecurityContext
    @Context
    protected SecurityContext securityContext;


    /**
     * 从上下文中直接获取自己(学生)的信息
     *
     * @return Student
     */
    protected Student getStudentSelf() {
        return (Student) securityContext.getUserPrincipal();
    }

    /**
     * 从上下文中直接获取自己(商家)的信息
     *
     * @return Trader
     */
    protected Trader getTraderSelf() {
        return (Trader) securityContext.getUserPrincipal();
    }
}
