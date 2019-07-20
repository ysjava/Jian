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
        if (mSavedInstanceState != null) {
            //如果不为空就已经保存了fragment,直接用tag去找回

        }else {
            //初始化三个主页面
            initFragments();

        }


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new HomeFragment())
                .commit();
    }

    private void initFragments() {

    }
}
