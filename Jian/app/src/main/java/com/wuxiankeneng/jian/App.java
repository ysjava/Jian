package com.wuxiankeneng.jian;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.jian.di.component.AppComponent;
import com.wuxiankeneng.jian.di.component.DaggerAppComponent;
import com.wuxiankeneng.jian.di.module.AppModule;

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
