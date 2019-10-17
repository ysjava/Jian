package com.wuxiankeneng.factory.model;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by White paper on 2019/10/17
 * Describe : 我的  界面中的item的model
 */
public class MyItemModel {
   private Drawable drawable;
   private String name;

    public MyItemModel(Drawable drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
