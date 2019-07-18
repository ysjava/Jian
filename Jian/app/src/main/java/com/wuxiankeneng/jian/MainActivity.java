package com.wuxiankeneng.jian;


import com.wuxiankeneng.common.app.BaseActivity;
import com.wuxiankeneng.jian.fragment.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new HomeFragment())
                .commit();
    }
}
