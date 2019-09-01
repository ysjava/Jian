package com.wuxiankeneng.jian.bean.base;

import com.google.gson.annotations.Expose;
import com.wuxiankeneng.jian.utils.TextUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个推送的具体Model，可以添加多个实体
 * 每次推送的详细数据是：把实体数组进行Json操作，然后发送Json字符串
 * 这样做的目的是：减少多次推送，如果有多个消息需要推送可以合并进行
 */
public class PushModel {
    public static final int ENTITY_TYPE_LOGOUT = -1;
    public static final int ENTITY_TYPE_MESSAGE = 200;
    public static final int ENTITY_TYPE_ORDER = 300;

    private List<Entity> entities = new ArrayList<>();

    public PushModel add(Entity entity) {
        entities.add(entity);
        return this;
    }

    public PushModel add(int type, String content) {
        return add(new Entity(type, content));
    }

    public String getPushString() {
        if (entities.size() == 0)
            return null;
        return TextUtil.toJson(entities);
    }


    /**
     * 具体的实体类型，在这个实体中包装了实体的内容和类型
     * 比如添加好友的推送：
     * content：用户信息的Json字符串
     * type=ENTITY_TYPE_ADD_FRIEND
     */
    public static class Entity {
        public Entity(int type, String content) {
            this.type = type;
            this.content = content;
        }

        // 消息类型
        @Expose
        public int type;
        // 消息实体
        @Expose
        public String content;
        // 消息生成时间
        @Expose
        public LocalDateTime createAt = LocalDateTime.now();
    }
}
