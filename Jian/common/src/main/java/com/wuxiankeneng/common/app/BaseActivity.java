package com.wuxiankeneng.common.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    protected ForceOffReceiver receiver;

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

            ActivityCollector.addActivity(this);
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

        //注册广播接收器  用于退出用户
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wuxiankeneng.jian.FORCE_OFF");
        receiver = new ForceOffReceiver();
        registerReceiver(receiver, intentFilter);
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

    @Override
    protected void onPause() {
        super.onPause();
        //注销广播接收器
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
        this.mPlaceHolderView = placeHolderView;
    }

    class ForceOffReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("是否退出登陆?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
//                    context.startActivity(Accoun);
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }
    }
}
