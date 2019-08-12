package com.wuxiankeneng.jian.bean.base;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.bean.api.account.TraderAccountRspModel;


import java.io.Serializable;
import java.time.LocalDateTime;

public class ResponseModel<M> implements Serializable {
    // 成功
    public static final int SUCCEED = 1;
    // 未知错误
    public static final int ERROR_UNKNOWN = 0;

    // 账户Token错误，需要重新登录
    public static final int ERROR_ACCOUNT_TOKEN = 2001;
    // 账户登录失败
    public static final int ERROR_ACCOUNT_LOGIN = 2002;
    // 账户注册失败
    public static final int ERROR_ACCOUNT_REGISTER = 2003;
    // 没有权限操作
    public static final int ERROR_ACCOUNT_NO_PERMISSION = 2010;
    // 请求参数错误
    public static final int ERROR_PARAMETERS = 4001;
    // 请求参数错误-已存在账户
    public static final int ERROR_PARAMETERS_EXIST_ACCOUNT = 4002;
    // 请求参数错误-已存在名称
    public static final int ERROR_PARAMETERS_EXIST_NAME = 4003;
    // 请求参数错误-已存在店铺名称
    public static final int ERROR_PARAMETERS_EXIST_SHOP_NAME = 4004;

    // 绑定pushId失败
    public static final int ERROR_BIND_PUSH_ID = 3001;
    // 服务器错误
    public static final int ERROR_SERVER = 3002;
    // xx未找到错误
    public static final int ERROR_XX_NOTFOUND = 3003;
    @Expose
    private int code;
    @Expose
    private String message;
    @Expose
    private LocalDateTime time = LocalDateTime.now();
    @Expose
    private M result;

    public ResponseModel() {
        this.code = 1;
        this.message = "ok";
    }

    public ResponseModel(M result) {
        this();
        this.result = result;
    }

    public ResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseModel(int code, String message, M result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }


    public boolean isSucceed() {
        return code == SUCCEED;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public M getResult() {
        return result;
    }

    public void setResult(M result) {
        this.result = result;
    }

    public static <M> ResponseModel<M> buildOk() {
        return new ResponseModel<>();
    }

    public static <M> ResponseModel<M> buildOk(M result) {
        return new ResponseModel<>(result);
    }

    public static <M> ResponseModel<M> buildAccountError() {
        return new ResponseModel<>(ERROR_ACCOUNT_TOKEN, "未登陆,请先登陆后访问");
    }

    public static <M> ResponseModel<M> buildParameterError() {
        return new ResponseModel<>(ERROR_PARAMETERS, "参数错误");
    }

    public static <M> ResponseModel<M> buildHaveAccountError() {
        return new ResponseModel<>(ERROR_PARAMETERS_EXIST_ACCOUNT, "当前账户已经存在");
    }

    public static <M> ResponseModel<M> buildHaveNameError() {
        return new ResponseModel<>(ERROR_PARAMETERS_EXIST_NAME, "昵称已经存在");
    }

    public static <M> ResponseModel<M> buildHaveShopNameError() {
        return new ResponseModel<>(ERROR_PARAMETERS_EXIST_SHOP_NAME, "店铺名已经存在");
    }

    public static <M> ResponseModel<M> buildRegisterError() {
        return new ResponseModel<>(ERROR_ACCOUNT_REGISTER, "有这个账户了");
    }

    public static <M> ResponseModel<M> buildBindServiceError() {
        return new ResponseModel<>(ERROR_BIND_PUSH_ID, "绑定错误,可能是服务器问题,稍后再试");
    }

    public static <M> ResponseModel<M> buildLoginError() {
        return new ResponseModel<>(ERROR_ACCOUNT_LOGIN, "登陆失败,账户或密码错误");
    }

    public static <M> ResponseModel<M> buildServiceError() {
        return new ResponseModel<>(ERROR_SERVER, "服务器异常,稍后再试");
    }

    public static <M> ResponseModel<M> buildError(String str) {
        return new ResponseModel<>(ERROR_XX_NOTFOUND, str);
    }
}
