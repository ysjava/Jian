package com.wuxiankeneng.jian;

import com.wuxiankeneng.jian.provider.AuthRequestFilter;
import com.wuxiankeneng.jian.provider.GsonProvider;
import com.wuxiankeneng.jian.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

public class Application extends ResourceConfig  {

    public Application() {
        //注册逻辑处理的包名
        packages(AccountService.class.getPackage().getName());
        //注册Gson解析器
        register(GsonProvider.class);
        //注册日志打印
        register(Logger.class);
        // 注册我们的全局请求拦截器
        register(AuthRequestFilter.class);
    }


}
