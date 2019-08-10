package com.wuxiankeneng.jian.factory;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.db.Trader;
import com.wuxiankeneng.jian.utils.Hib;
import com.wuxiankeneng.jian.utils.TextUtil;

import java.util.List;
import java.util.UUID;

public class TraderFactory {
    public static Trader findByToken(String token) {

        return Hib.query(session -> (Trader) session
                .createQuery("from Trader where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }

    public static Trader findByPhone(String phone) {
        return Hib.query(session -> (Trader) session
                .createQuery("from Trader where phone=:phone")
                .setParameter("phone", phone)
                .uniqueResult());
    }

    public static Trader findByName(String name) {
        return Hib.query(session -> (Trader) session
                .createQuery("from Trader where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    // 通过Name找到User
    public static Trader findById(String id) {
        // 通过Id查询，更方便
        return Hib.query(session -> session.get(Trader.class, id));
    }

    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库中的User信息
     *
     * @param phone    账户
     * @param password 密码
     * @param name     用户名
     * @return User
     */
    public static Trader register(String phone, String password, String name) {
        // 去除账户中的首位空格
        phone = phone.trim();
        // 处理密码
        password = encodePassword(password);
        Trader trader = createTrader(phone, password, name);
        if (trader != null) {
            trader = login(trader);
        }
        return trader;
    }

    /**
     * 注册部分的新建用户逻辑
     *
     * @param phone    手机号
     * @param password 加密后的密码
     * @param name     用户名
     * @return 返回一个商人
     */
    private static Trader createTrader(String phone, String password, String name) {
        Trader trader = new Trader();
        trader.setName(name);
        trader.setPassword(password);
        trader.setPhone(phone);

        // 数据库存储
        return Hib.query(session -> {
            session.save(trader);
            return trader;
        });
    }

    /**
     * 把一个Trader进行登录操作
     * 本质上是对Token进行操作
     *
     * @param trader trader
     * @return Trader
     */
    private static Trader login(Trader trader) {
        // 使用一个随机的UUID值充当Token
        String newToken = UUID.randomUUID().toString();
        // 进行一次Base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        trader.setToken(newToken);

        return update(trader);
    }

    /**
     * 更新用户信息到数据库
     *
     * @param trader User
     * @return User
     */
    private static Trader update(Trader trader) {
        return Hib.query(session -> {
            session.saveOrUpdate(trader);
            return trader;
        });
    }

    public static Trader bindPushId(Trader trader, String pushId) {
        if (Strings.isNullOrEmpty(pushId))
            return null;
        // 第一步，查询是否有其他账户绑定了这个设备
        // 取消绑定，避免推送混乱
        // 查询的列表不能包括自己
        Hib.queryOnly(session -> {
            @SuppressWarnings("unchecked")
            List<Trader> traderList = (List<Trader>) session
                    .createQuery("from Trader where lower(pushId)=:pushId and id!=:traderId")
                    .setParameter("pushId", pushId.toLowerCase()) //toLowerCase将大写字符转换为小写
                    .setParameter("traderId", trader.getId())
                    .list();

            for (Trader u : traderList) {
                // 更新为null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if (pushId.equalsIgnoreCase(trader.getPushId())) {
            // 如果当前需要绑定的设备Id，之前已经绑定过了
            // 那么不需要额外绑定
            return trader;
        } else {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(trader.getPushId())) {
                // 推送一个退出消息
//                PushFactory.pushLogout(user, user.getPushId());
            }

            // 更新新的设备Id
            trader.setPushId(pushId);
            return update(trader);
        }
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

    /**
     * 使用账户密码进行登陆
     */
    public static Trader login(String phone, String password) {
        //空格处理
        final String phoneStr = phone.trim();
        //密码是进行过处理的,所以要想匹配就的进行同样的而处理
        final String encodePassword = encodePassword(password);

        Trader trader = Hib.query(session -> (Trader) session
                .createQuery("from Trader where phone=:phone and password=:password")
                .setParameter("phone", phoneStr)
                .setParameter("password", encodePassword)
                .uniqueResult());

        if (trader != null) {
            trader = login(trader);
        }

        return trader;
    }
}
