package com.wuxiankeneng.common.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by White paper on 2019/10/17
 * Describe : activity的管理
 */
public class ActivityCollector {
    public static List<BaseActivity> activities = new ArrayList<>();

    public static void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (BaseActivity activity : activities) {
            if (!activity.isFinishing())
                activity.finish();
        }
        activities.clear();
    }
}
