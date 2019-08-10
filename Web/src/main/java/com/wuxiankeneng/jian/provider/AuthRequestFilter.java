package com.wuxiankeneng.jian.provider;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.bean.db.Trader;
import com.wuxiankeneng.jian.factory.StudentFactory;
import com.wuxiankeneng.jian.factory.TraderFactory;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class AuthRequestFilter implements ContainerRequestFilter {
    // 实现接口的过滤方法
    @Override
    public void filter(ContainerRequestContext requestContext) {
        // 检查是否是登录注册接口
        String relationPath = ((ContainerRequest) requestContext).getPath(false);
        if (relationPath.startsWith("account/login")
                || relationPath.startsWith("account/register")) {
            // 直接走正常逻辑，不做拦截
            return;
        }


        //商家的访问
        if (relationPath.startsWith("trader") || relationPath.startsWith("account/bind/trader")) {

            // 从Headers中去找到第一个token节点
            String token = requestContext.getHeaders().getFirst("token");
            if (!Strings.isNullOrEmpty(token)) {

                // 查询自己的信息
                final Trader trader = TraderFactory.findByToken(token);
                if (trader != null) {
                    // 给当前请求添加一个上下文
                    requestContext.setSecurityContext(new MySecurityContext(trader));
                    // 写入上下文后就返回
                    return;
                }
            }
        }

        //学生的访问
        if (relationPath.startsWith("student") || relationPath.startsWith("account/bind/student")) {

            // 从Headers中去找到第一个token节点
            String token = requestContext.getHeaders().getFirst("token");
            if (!Strings.isNullOrEmpty(token)) {

                // 查询自己的信息
                final Student student = StudentFactory.findByToken(token);
                if (student != null) {
                    // 给当前请求添加一个上下文
                    requestContext.setSecurityContext(new MySecurityContext(student));
                    // 写入上下文后就返回
                    return;
                }
            }
        }


        // 直接返回一个账户需要登录的Model
        ResponseModel model = ResponseModel.buildAccountError();
        // 构建一个返回
        Response response = Response.status(Response.Status.OK)
                .entity(model)
                .build();
        // 拦截，停止一个请求的继续下发，调用该方法后之间返回请求
        // 不会走到Service中去
        requestContext.abortWith(response);

    }

    class MySecurityContext implements SecurityContext {
        private Student student;
        private Trader trader;

        MySecurityContext(Student student) {
            this.student = student;
        }

        MySecurityContext(Trader trader) {
            this.trader = trader;
        }

        @Override
        public Principal getUserPrincipal() {
            return student == null ? trader : student;
        }

        @Override
        public boolean isUserInRole(String role) {
            // 可以在这里写入用户的权限，role 是权限名，
            // 可以管理管理员权限等等
            return true;
        }

        @Override
        public boolean isSecure() {
            // 默认false即可，HTTPS
            return false;
        }

        @Override
        public String getAuthenticationScheme() {
            // 不用理会
            return null;
        }
    }
}
