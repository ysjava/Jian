package com.wuxiankeneng.jian;

import com.igexin.sdk.PushManager;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.jian.di.component.AppComponent;
import com.wuxiankeneng.jian.di.component.DaggerAppComponent;
import com.wuxiankeneng.jian.di.module.AppModule;
import com.wuxiankeneng.jian.service.DemoIntentService;
import com.wuxiankeneng.jian.service.DemoPushService;

import org.litepal.LitePal;

/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public class App  extends Application {
    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        LitePal.getDatabase();

// 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(getInstance()))
                    .build();
        }
        return appComponent;
    }
}
