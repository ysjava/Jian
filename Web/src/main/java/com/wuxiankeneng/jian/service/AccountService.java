package com.wuxiankeneng.jian.service;


import com.google.common.base.Strings;


import com.wuxiankeneng.jian.bean.api.account.*;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.db.*;

import com.wuxiankeneng.jian.factory.AdminFactory;
import com.wuxiankeneng.jian.factory.SchoolFactory;
import com.wuxiankeneng.jian.factory.StudentFactory;
import com.wuxiankeneng.jian.factory.TraderFactory;
import com.wuxiankeneng.jian.utils.Hib;
import com.wuxiankeneng.jian.utils.TextUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/account")
public class AccountService extends BaseService {

    @GET
    @Path("test")
    public ResponseModel<String> get() {
        List<TestCall> list = new ArrayList<>();
        TestCall call = new TestCall();
        call.setName("我要成为大佬");
        TestCall call2 = new TestCall();
        call2.setName("我要成为超级大佬");
        list.add(call);
        list.add(call2);
        return ResponseModel.buildOk(TextUtil.toJson(list));
    }

    //商人注册
    @POST
    @Path("/register/trader")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<TraderAccountRspModel> traderRegister(RegisterByTraderModel model) {
        if (!RegisterByTraderModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        //先进行数据库查找此账号是否注册
        Trader trader = TraderFactory.findByPhone(model.getPhone().trim());
        if (trader != null) {
            return ResponseModel.buildHaveAccountError();
        }
        //检查此名字是否存在
        trader = TraderFactory.findByName(model.getName().trim());
        if (trader != null) {
            return ResponseModel.buildHaveNameError();
        }
        //注册操作   完成得到一个用户
        trader = TraderFactory.register(model.getPhone(), model.getPassword(), model.getName());

        if (trader != null) {
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                //进行绑定推送id
                return bindForTrader(trader, model.getPushId());
            }
            //没有pushId的话就不绑定了,
            TraderAccountRspModel accountRspModel = new TraderAccountRspModel(trader);
            return ResponseModel.buildOk(accountRspModel);
        } else
            return ResponseModel.buildRegisterError();
    }


    //商人登陆
    @POST
    @Path("/login/trader")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<TraderAccountRspModel> traderLogin(LoginByTraderModel model) {
        if (!LoginByTraderModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        Trader trader = TraderFactory.login(model.getPhone(), model.getPassword());

        if (trader != null) {
            if (model.getPushId() != null) {
                return bindForTrader(trader, model.getPushId());
            }
            return ResponseModel.buildOk(new TraderAccountRspModel(trader, false));
        } else
            return ResponseModel.buildLoginError();
    }


    //  绑定设备Id 商人
    @POST
    @Path("/bind/trader/{pushId}")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 从请求头中获取token字段
    // pushId从url地址中获取
    public ResponseModel<TraderAccountRspModel> bindForTrader(@HeaderParam("token") String token,
                                                              @PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(token) ||
                Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        // 拿到自己的个人信息
        Trader trader = TraderFactory.findByToken(token);
        if (trader == null)
            return ResponseModel.buildAccountError();//没找到就代表token过期了

        return bindForTrader(trader, pushId);
    }

    /**
     * 绑定操作 :商人
     */
    private ResponseModel<TraderAccountRspModel> bindForTrader(Trader self, String pushId) {
        Trader trader = TraderFactory.bindPushId(self, pushId);
        if (trader != null)
            return ResponseModel.buildOk(new TraderAccountRspModel(trader, true));
        else
            return ResponseModel.buildBindServiceError();
    }


    //学生注册
    @POST
    @Path("/register/student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<StudentAccountRspModel> studentRegister(RegisterByStudentModel model) {
        if (!RegisterByStudentModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        //先进行数据库查找此账号是否注册
        Student student = StudentFactory.findByPhone(model.getPhone().trim());
        if (student != null) {
            return ResponseModel.buildHaveAccountError();
        }

        //检查此名字是否存在
        student = StudentFactory.findByName(model.getName().trim());
        if (student != null) {
            return ResponseModel.buildHaveNameError();
        }

        //检查此学校是否存在
        School school = SchoolFactory.findById(model.getSchoolId());
        if (school == null) {
            return ResponseModel.buildParameterError();
        }

        //注册操作   完成得到一个用户
        student = StudentFactory.register(model.getPhone(), model.getPassword(), model.getName(), school);

        if (student != null) {
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                //进行绑定推送id
                return bindForStudent(student, model.getPushId());
            }
            //没有pushId的话就不绑定了,
            StudentAccountRspModel accountRspModel = new StudentAccountRspModel(student);
            return ResponseModel.buildOk(accountRspModel);
        } else
            return ResponseModel.buildRegisterError();
    }


    //学生登陆
    @POST
    @Path("/login/student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<StudentAccountRspModel> studentLogin(LoginByStudentModel model) {
        if (!LoginByStudentModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        Student student = StudentFactory.login(model.getPhone(), model.getPassword());

        if (student != null) {
            if (model.getPushId() != null) {
                return bindForStudent(student, model.getPushId());
            }
            return ResponseModel.buildOk(new StudentAccountRspModel(student, false));
        } else
            return ResponseModel.buildLoginError();
    }

    //  绑定设备Id 学生
    @POST
    @Path("/bind/student/{pushId}")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 从请求头中获取token字段
    // pushId从url地址中获取
    public ResponseModel<StudentAccountRspModel> bindForStudent(@HeaderParam("token") String token,
                                                                @PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(token) ||
                Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        // 拿到自己的个人信息
        Student student = StudentFactory.findByToken(token);
        if (student == null)
            return ResponseModel.buildAccountError();//没找到就代表token过期了

        return bindForStudent(student, pushId);
    }

    /**
     * 绑定操作 :学生
     */
    private ResponseModel<StudentAccountRspModel> bindForStudent(Student self, String pushId) {
        Student student = StudentFactory.bindPushId(self, pushId);
        if (student != null)
            return ResponseModel.buildOk(new StudentAccountRspModel(student, true));
        else
            return ResponseModel.buildBindServiceError();
    }

    @POST
    @Path("/register/admin/{account}|{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel adminRegister(@PathParam("account") String account, @PathParam("password") String password) {
        if (Strings.isNullOrEmpty(account) && Strings.isNullOrEmpty(account))
            return ResponseModel.buildParameterError();

        Admin admin = AdminFactory.findByAccount(account.trim());
        if (admin != null) {
            return ResponseModel.buildHaveAccountError();
        }
        admin = AdminFactory.register(account, password);
        if (admin == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk();

    }

    @POST
    @Path("/login/admin/{account}|{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel adminLogin(@PathParam("account") String account, @PathParam("password") String password) {
        if (Strings.isNullOrEmpty(account) && Strings.isNullOrEmpty(account))
            return ResponseModel.buildParameterError();

        Admin admin = AdminFactory.login(account, password);
        if (admin == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk();

    }
}
