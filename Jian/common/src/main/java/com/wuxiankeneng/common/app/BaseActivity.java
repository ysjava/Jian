package com.wuxiankeneng.common.app;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wuxiankeneng.common.widget.PlaceHolderView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by White paper on 2019/6/14
 * Describe : 基类activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Bundle mSavedInstanceState;
    protected PlaceHolderView mPlaceHolderView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        //在界面未初始化之前初始化窗口
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initBefore();
            initWidget();
            initData();
        }
    }



    //初始化窗口
    protected void initWindows() {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


    }

    //初始化相关参数
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    //从实现类拿到id
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件调用之前
     */
    protected void initBefore() {
    }

    //初始化数据
    protected void initData() {
    }

    //初始化控件
    protected void initWidget() {
        //直接是activity中的view  ,不用像fragment那样需要view
        ButterKnife.bind(this);
    }

    //点击导航栏的返回键
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    //点击实体键的返回键

    @Override
    public void onBackPressed() {
        //得到当前activity下的所以fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment) {
                    //判断是否拦截了返回按钮   就是实现...recycler.Fragment类的fragment是否重写了onBackPressed方法
                    //重写了就执行其方法体然后返回true,这儿收到就直接return
                    if (((BaseFragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }
        //TODO   测试把super放第一行
        super.onBackPressed();
        finish();

    }

    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.mPlaceHolderView = placeHolderView;
    }
}
