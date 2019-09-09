package com.wuxiankeneng.jian.factory;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.db.Address;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.utils.Hib;
import com.wuxiankeneng.jian.utils.TextUtil;

import java.util.List;
import java.util.UUID;

public class StudentFactory {
    public static Student findByToken(String token) {
        return Hib.query(session -> (Student) session
                .createQuery("from Student where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }

    public static Student findByPhone(String phone) {
        return Hib.query(session -> (Student) session
                .createQuery("from Student where phone=:phone")
                .setParameter("phone", phone)
                .uniqueResult());
    }

    public static Student findByName(String name) {
        return Hib.query(session -> (Student) session
                .createQuery("from Student where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    // 通过Name找到User
    public static Student findById(String id) {
        // 通过Id查询，更方便
        return Hib.query(session -> session.get(Student.class, id));
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
    public static Student register(String phone, String password, String name, School school) {
        // 去除账户中的首位空格
        phone = phone.trim();
        // 处理密码
        password = encodePassword(password);
        Student student = createTrader(phone, password, name, school);
        if (student != null) {
            student = login(student);
        }
        return student;
    }

    /**
     * 注册部分的新建用户逻辑
     *
     * @param phone    手机号
     * @param password 加密后的密码
     * @param name     用户名
     * @return 返回一个商人
     */
    private static Student createTrader(String phone, String password, String name, School school) {
        Student student = new Student();
        student.setName(name);
        student.setPassword(password);
        student.setPhone(phone);
        student.setSchool(school);
        // 数据库存储
        return Hib.query(session -> {
            session.save(student);
            return student;
        });
    }

    /**
     * 把一个Student进行登录操作
     * 本质上是对Token进行操作
     *
     * @param student trader
     * @return Student
     */
    private static Student login(Student student) {
        // 使用一个随机的UUID值充当Token
        String newToken = UUID.randomUUID().toString();
        // 进行一次Base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        student.setToken(newToken);

        return update(student);
    }

    /**
     * 更新用户信息到数据库
     *
     * @param student User
     * @return User
     */
    private static Student update(Student student) {
        return Hib.query(session -> {
            session.saveOrUpdate(student);
            return student;
        });
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


    @SuppressWarnings("Duplicates")
    public static Student bindPushId(Student student, String pushId) {
        if (Strings.isNullOrEmpty(pushId))
            return null;
        // 第一步，查询是否有其他账户绑定了这个设备
        // 取消绑定，避免推送混乱
        // 查询的列表不能包括自己
        Hib.queryOnly(session -> {
            @SuppressWarnings("unchecked")
            List<Student> studentList = (List<Student>) session
                    .createQuery("from Student where lower(pushId)=:pushId and id!=:studentId")
                    .setParameter("pushId", pushId.toLowerCase()) //toLowerCase将大写字符转换为小写
                    .setParameter("studentId", student.getId())
                    .list();

            for (Student u : studentList) {
                // 更新为null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if (pushId.equalsIgnoreCase(student.getPushId())) {
            // 如果当前需要绑定的设备Id，之前已经绑定过了
            // 那么不需要额外绑定
            return student;
        } else {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(student.getPushId())) {
                // 推送一个退出消息
//                PushFactory.pushLogout(student, student.getPushId());
            }

            // 更新新的设备Id
            student.setPushId(pushId);
            return update(student);
        }
    }

    /**
     * 使用账户密码进行登陆
     */
    public static Student login(String phone, String password) {
        //空格处理
        String phoneStr = phone.trim();
        //密码是进行过处理的,所以要想匹配就的进行同样而处理
        String encodePassword = encodePassword(password);

        Student student = Hib.query(session -> (Student) session
                .createQuery("from Student where phone=:phone and password=:password")
                .setParameter("phone", phoneStr)
                .setParameter("password", encodePassword)
                .uniqueResult());

        if (student != null) {
            student = login(student);
        }

        return student;
    }


    @SuppressWarnings("unchecked")
    public static List<Address> getAddressList(Student student) {
        return Hib.query(session -> session.createQuery("from Address where student=:student")
                .setParameter("student", student)
                .list());
    }

    public static Address findAddressById(String id) {
        return Hib.query(session -> session.get(Address.class,id));
    }
}
