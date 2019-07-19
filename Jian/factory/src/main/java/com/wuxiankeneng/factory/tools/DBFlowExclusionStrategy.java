package com.wuxiankeneng.factory.tools;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


/**
 * Created by White paper on 2019/6/15
 * Describe :
 */
public class DBFlowExclusionStrategy  implements ExclusionStrategy {
//    @Override
//    public boolean shouldSkipField(FieldAttributes f) {
//        // 被跳过的字段
//        // 只要是属于DBFlow数据的
//        return f.getDeclaredClass().equals(ModelAdapter.class);
//    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        // 别跳过的Class
        return false;
    }
}
