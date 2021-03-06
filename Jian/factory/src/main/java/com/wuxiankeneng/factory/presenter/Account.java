package com.wuxiankeneng.factory.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.db.Student;
import com.wuxiankeneng.factory.model.account.AccountRspModel;

import org.litepal.LitePal;


/**
 * Created by White paper on 2019/6/14
 * Describe :
 */
public class Account {
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    // 设备的推送Id
    private static String pushId;
    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;
    // 登录状态的Token，用来接口请求
    private static String token;
    // 登录的学生ID
    private static String stuId;
    // 登录的账户
    private static String account;

    /**
     * 存储数据到XML文件，持久化
     */
    private static void save(Context context) {
        // 获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        // 存储数据
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, stuId)
                .putString(KEY_ACCOUNT, account)
                .apply();
    }

    /**
     * 删除数据  XML文件
     */
    private static void delete(Context context) {
        // 获取数据持久化的SP
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        // 存储数据
        sp.edit()
                .clear()
                .apply();
    }

    /**
     * 进行数据加载
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
        token = sp.getString(KEY_TOKEN, "");
        stuId = sp.getString(KEY_USER_ID, "");
        account = sp.getString(KEY_ACCOUNT, "");
    }


    /**
     * 设置并存储设备的Id
     *
     * @param pushId 设备的推送ID
     */
    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        Account.save(Factory.app());
    }

    /**
     * 获取推送Id
     *
     * @return 推送Id
     */
    public static String getPushId() {
        return pushId;
    }


    /**
     * 返回当前账户是否登录
     *
     * @return True已登录
     */
    public static boolean isLogin() {
        // 用户Id 和 Token 不为空
        return !TextUtils.isEmpty(stuId)
                && !TextUtils.isEmpty(token);
    }


    /**
     * 是否已经完善了用户信息
     *
     * @return True 是完成了
     */
//    public static boolean isComplete() {
//        // 首先保证登录成功
//        if (isLogin()) {
//            Student self = getUser();
//            return !TextUtils.isEmpty(self.getStuPortrait())
//                    && self.getSex() != 0;
//        }
//        // 未登录返回信息不完全
//        return false;
//    }

    /**
     * 是否已经绑定到了服务器
     *
     * @return True已绑定
     */
    public static boolean isBind() {
        return isBind;
    }

    /**
     * 设置绑定状态
     */
    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save(Factory.app());
    }

    /**
     * 保存我自己的信息到持久化XML中
     *
     * @param model AccountRspModel
     */
    public static void login(final AccountRspModel model) {
        // 存储当前登录的账户, token, 用户Id，方便从数据库中查询我的信息
        Account.token = model.getToken();
        Account.account = model.getPhone();
        Account.stuId = model.getCard().getId();
        save(Factory.app());
    }

    /**
     * 退出操作
     *
     */
//    public static void signOut(Context context, Intent intent) {
//        // 删除xml持续化文件
//        delete(Factory.app());
//        //数据初始化
//        load(context);
//        //删除数据库
//        AppDatabase.delete();
//        //关闭所有activity
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//
//    }


    /**
     * 获取当前登录的用户信息
     *
     * @return User
     */
    public static Student getUser() {
        // 如果为null返回一个new的User，其次从数据库查询
        return TextUtils.isEmpty(stuId) ? new Student() : LitePal.where("sId=?", stuId).findFirst(Student.class);
    }
//
//    public static Student getSelf() {
//        // 返回自己
//        return TextUtils.isEmpty(stuId) ? null : SQLite.select()
//                .from(Student.class)
//                .where(Student_Table.stuId.eq(stuId))
//                .querySingle();
//    }

    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    public static String getToken() {
        return token;
    }

    //得到用户id
    public static String getUserId() {
        return stuId;
    }
}
