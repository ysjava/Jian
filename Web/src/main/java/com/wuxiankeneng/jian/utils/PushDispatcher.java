package com.wuxiankeneng.jian.utils;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.base.PushModel;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.bean.db.Trader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("Duplicates")
public class PushDispatcher {
    //接受者类型
    public static final int PUSH_TYPE_TRADER = 0;
    public static final int PUSH_TYPE_STUDENT = 1;

    //给商家端推送的配置信息
    private static final String appId = "7N5sGbsSrn8bofa5T5jbd3";
    private static final String appKey = "LELjln5ybv9A8hTR9Ikrp9";
    private static final String masterSecret = "NX9kKYnhnn9nEWtVPWkBS2";

    //给学生端推送的配置信息
    private static final String appId2 = "F48FFgv3l4AkYlw9s2hsd8";
    private static final String appKey2 = "lYEJLeOg8865l6RazSKQO9";
    private static final String masterSecret2 = "HueXzxBNXy64uABGGauoj8";


    private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";

    private final IGtPush pusher;
    // 要收到消息的人和内容的列表
    private final List<BatchBean> beans = new ArrayList<>();
    private final int pushType;

    //pushType 推送的类型,给谁推送  0 代表商家, 1代表学生
    public PushDispatcher(int pushType) {
        this.pushType = pushType;
        this.pusher = new IGtPush(host, pushType == 0 ? appKey : appKey2, pushType == 0 ? masterSecret : masterSecret2);

    }


    /**
     * 添加一个要推送的订单信息
     */
    public boolean add(Trader trader, PushModel model) {
        // 基础检查，必须有接收者的设备的Id
        if (trader == null || model == null ||
                Strings.isNullOrEmpty(trader.getPushId()))
            return false;

        String pushStr = model.getPushString();
        if (Strings.isNullOrEmpty(pushStr))
            return false;
        // 构建一个目标+内容
        BatchBean bean = buildMessage(trader.getPushId(), pushStr);
        beans.add(bean);
        return true;
    }

    /**
     * 商家处理订单进度后给学生的推送
     */
    public boolean add(Student student, PushModel model) {
        // 基础检查，必须有接收者的设备的Id
        if (student == null || model == null ||
                Strings.isNullOrEmpty(student.getPushId()))
            return false;

        String pushStr = model.getPushString();
        if (Strings.isNullOrEmpty(pushStr))
            return false;
        // 构建一个目标+内容
        BatchBean bean = buildMessage(student.getPushId(), pushStr);
        beans.add(bean);
        return true;
    }


    /**
     * 对要发送的数据进行格式化封装
     *
     * @param clientId 接收者的设备Id
     * @param text     要接收的数据
     * @return BatchBean
     */
    private BatchBean buildMessage(String clientId, String text) {
        // 透传消息，不是通知栏显示，而是在MessageReceiver收到
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(pushType == 0 ? appId : appId2);
        template.setAppkey(pushType == 0 ? appKey : appKey2);
        template.setTransmissionContent(text);
        template.setTransmissionType(0); //这个Type为int型，填写1则自动启动app

        SingleMessage message = new SingleMessage();
        message.setData(template); // 把透传消息设置到单消息模版中
        message.setOffline(true); // 是否运行离线发送
        message.setOfflineExpireTime(24 * 3600 * 1000); // 离线消息时常

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(pushType == 0 ? appId : appId2);
        target.setClientId(clientId);

        // 返回一个封装
        return new BatchBean(message, target);
    }


    // 进行消息最终发送
    public boolean submit() {
        //构建一个打包类
        IBatch batch = pusher.getBatch();
        //是否有数据需要发送
        boolean haveData = false;
        for (BatchBean bean : beans) {
            try {
                batch.add(bean.message, bean.target);
                haveData = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //没有数据就直接返回
        if (!haveData)
            return false;

        //推送结果
        IPushResult result = null;
        try {
            result = batch.submit();
        } catch (IOException e) {
            e.printStackTrace();
            // 失败情况下尝试重复发送一次
            try {
                batch.retry();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (result != null) {
            Logger.getLogger("PushDispatcher")
                    .log(Level.INFO, (String) result.getResponse().get("result"));
            return true;
        }
        Logger.getLogger("PushDispatcher")
                .log(Level.WARNING, "推送服务器响应异常！！！");
        return false;
    }


    //一个Bean封装
    private static class BatchBean {
        SingleMessage message;
        Target target;

        BatchBean(SingleMessage message, Target target) {
            this.message = message;
            this.target = target;
        }
    }
}
