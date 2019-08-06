package com.wuxiankeneng.factory.helper;


import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DbHelper {
    private static DbHelper instance;

    static {
        instance = new DbHelper();
    }


    /**
     * 观察者的集合
     * Class<?>观察的表
     * Set <ChangedListener>    每一个表对应的观察者有很多
     */
    private final Map<Class<?>, Set<ChangedListener>> changedListener = new HashMap<>();


    /**
     * 从所有观察者中 获取某一个表的所有观察者
     *
     * @param modelClass 观察的表的信息
     * @param <Model>    泛型
     * @return modelClass表的所有观察者
     */
    private <Model extends LitePalSupport> Set<ChangedListener> getListener(Class<Model> modelClass) {
        if (changedListener.containsKey(modelClass)) {
            return changedListener.get(modelClass);
        }
        return null;
    }

    /**
     * 添加一个观察者
     */
    public static <Model extends LitePalSupport> void addChangedListener(Class<Model> tClass, ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = instance.getListener(tClass);
        if (changedListeners == null) {
            //表示还观察者集合中不存在 类型为 tClass 的观察者集合  那就新建一个类型为 tClass的容器
            changedListeners = new HashSet<>();
            //添加到观察者集合中去
            instance.changedListener.put(tClass, changedListeners);
        }
        //表示所有的观察者集合中已经有了   tClass类型的观察者集合, 添加进tClass类型的观察者集合中去即可
        changedListeners.add(listener);

    }

    /**
     * 删除一个观察者
     *
     * @param tClass   观察者的类型
     * @param listener 观察者
     * @param <Model>  泛型
     */
    public static <Model extends LitePalSupport> void removeChangedListener(Class<Model> tClass, ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = instance.getListener(tClass);
        if (changedListeners == null)
            return;
        //移除
        changedListeners.remove(listener);
    }

    @SafeVarargs
    public static <Model extends LitePalSupport> void save(Class<Model> tClass, Model... models) {
        LitePal.saveAll(Arrays.asList(models));
        //通知保存,观察者进行相应更新
        instance.notifySave(tClass, models);

    }

    /**
     * @param tClass  通知的类型
     * @param models  通知的model          t
     * @param <Model> 泛型          s
     */
    //    @SafeVarargs
//    public static <Model extends LitePalSupport> void delete(Model... models) {
//        LitePal.deleteAll(Arrays.asList(models));
//    }
    @SuppressWarnings("unchecked")
    private final <Model extends LitePalSupport> void notifySave(Class<Model> tClass, Model... models) {
        Set<ChangedListener> changedListeners = instance.getListener(tClass);
        if (changedListeners != null && changedListeners.size() > 0) {
            for (ChangedListener<Model> listener : changedListeners) {
                listener.onDataSave(models);
            }
        }
    }

    /**
     * 通知监听器
     */
    @SuppressWarnings("unchecked")
    public interface ChangedListener<Data extends LitePalSupport> {
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }
}
