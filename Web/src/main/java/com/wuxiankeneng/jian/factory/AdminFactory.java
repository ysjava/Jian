package com.wuxiankeneng.jian.factory;

import com.wuxiankeneng.jian.bean.api.admin.RecommendModel;
import com.wuxiankeneng.jian.bean.db.Admin;
import com.wuxiankeneng.jian.bean.db.Recommend;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.utils.Hib;
import com.wuxiankeneng.jian.utils.TextUtil;

import java.util.UUID;

public class AdminFactory {
    public static Admin register(String account, String password) {
        // 去除账户中的首位空格
        account = account.trim();
        // 处理密码
        password = encodePassword(password);
        Admin admin = createAdmin(account, password);
        if (admin != null) {
            admin = login(admin);
        }
        return admin;
    }

    /**
     * 把一个Admin进行登录操作
     * 本质上是对Token进行操作
     *
     * @param admin admin
     * @return admin
     */
    private static Admin login(Admin admin) {
        // 使用一个随机的UUID值充当Token
        String newToken = UUID.randomUUID().toString();
        // 进行一次Base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        admin.setToken(newToken);

        return update(admin);
    }

    /**
     * 更新管理信息到数据库
     *
     * @param admin admin
     * @return admin
     */
    private static Admin update(Admin admin) {
        return Hib.query(session -> {
            session.saveOrUpdate(admin);
            return admin;
        });
    }


    /**
     * 注册部分的新建用户逻辑
     *
     * @param password 加密后的密码
     * @return 返回一个商人
     */
    private static Admin createAdmin(String account, String password) {
        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setPassword(password);
        // 数据库存储
        return Hib.query(session -> {
            session.save(admin);
            return admin;
        });
    }

    /**
     * 使用账户密码进行登陆
     */
    public static Admin login(String account, String password) {
        //空格处理
        String accountStr = account.trim();
        //密码是进行过处理的,所以要想匹配就的进行同样的处理
        String encodePassword = encodePassword(password);
        Admin admin = Hib.query(session -> (Admin) session
                .createQuery("from Admin where account=:account and password=:password")
                .setParameter("account", accountStr)
                .setParameter("password", encodePassword)
                .uniqueResult());

        if (admin != null) {
            admin = login(admin);
        }

        return admin;
    }

    /**
     * 对密码进行加密操作
     *
     * @param password 原文
     * @return 密文
     */
    private static String encodePassword(String password) {
        // 密码去除首位空格
        password = password.trim();
        // 进行MD5非对称加密，加盐会更安全，盐也需要存储
        password = TextUtil.getMD5(password);
        // 再进行一次对称的Base64加密，当然可以采取加盐的方案
        return TextUtil.encodeBase64(password);
    }

    public static Admin findByAccount(String account) {
        return Hib.query(session -> (Admin) session
                .createQuery("from Admin where account=:account")
                .setParameter("account", account)
                .uniqueResult());
    }

    public static Admin findByToken(String token) {
        return Hib.query(session -> (Admin) session
                .createQuery("from Admin where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }

    public static Recommend createRecommend(RecommendModel model, School school) {
        return Hib.query(session -> {
            Recommend recommend = new Recommend(school, model.getImgUrl(), model.getShopIdOrAdvertUrl(), model.getType());
            session.save(recommend);
            return recommend;
        });
    }
}
