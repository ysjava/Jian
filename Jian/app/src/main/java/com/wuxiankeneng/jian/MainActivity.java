package com.wuxiankeneng.jian;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.wuxiankeneng.jian.activity.BaseActivity;
import com.wuxiankeneng.jian.fragment.main.HomeFragment;
import com.wuxiankeneng.jian.fragment.main.MyFragment;
import com.wuxiankeneng.jian.fragment.main.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;
    private Fragment mCurrent; //用户保存内存回收 MainActivity销毁后保存当前显示的fragment,用于重启后的找回
    private List<Fragment> mFragments = new ArrayList<>(); //保存当前活动下的f,用于配合mCurrent的恢复
    private static final String SAVE_CURRENT_FRAGMENT_TAG = "TAG";
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    //是否时需要跳转到当前活动的其它界面
    private boolean isJump = false;

    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("JUMP", true);
        context.startActivity(intent);
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null)
            isJump = bundle.getBoolean("JUMP", false);
        return super.initArgs(bundle);
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        if (mSavedInstanceState != null) {
            //如果不为空就表示activity被系统回收了,在save...里面保存了fragment,直接用tag去找回
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //找到f并进行赋值,然后拿到回收前的frag
            Fragment saveFragment = findFragments();

            for (Fragment fragment : mFragments) {
                if (fragment == saveFragment) {
                    transaction = transaction.show(fragment);//表示这个f与保存的相同,那么就显示这个,否则隐藏
                } else
                    transaction = transaction.hide(fragment);
            }
            transaction.commit();
        } else {
            //初始化三个主页面
            initFragments();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.lay_container, homeFragment, HomeFragment.class.getSimpleName())
                    .add(R.id.lay_container, orderFragment, OrderFragment.class.getSimpleName())
                    .add(R.id.lay_container, myFragment, MyFragment.class.getSimpleName())
                    .show(homeFragment)
                    .hide(orderFragment)
                    .hide(myFragment)
                    .commit();
        }
        mNavigationView.setItemHorizontalTranslationEnabled(false);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        if (isJump)//跳转到订单界面
            mNavigationView.setSelectedItemId(R.id.ic_order);
    }

    private Fragment findFragments() {
        //当内存回收后重启,拿到保存的回收前的frag的tag
        String tag = mSavedInstanceState.getString(SAVE_CURRENT_FRAGMENT_TAG);
        Fragment getSaveFragment = getSupportFragmentManager().findFragmentByTag(tag);

        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        orderFragment = (OrderFragment) getSupportFragmentManager().findFragmentByTag(OrderFragment.class.getSimpleName());
        myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(MyFragment.class.getSimpleName());

        mFragments.add(homeFragment);
        mFragments.add(orderFragment);
        mFragments.add(myFragment);

        return getSaveFragment;
    }

    private void initFragments() {
        homeFragment = (HomeFragment) HomeFragment.instantiate(MainActivity.this, HomeFragment.class.getName());
        orderFragment = (OrderFragment) OrderFragment.instantiate(MainActivity.this, OrderFragment.class.getName());
        myFragment = (MyFragment) MyFragment.instantiate(MainActivity.this, MyFragment.class.getName());

        //保存
        mFragments.add(homeFragment);
        mFragments.add(orderFragment);
        mFragments.add(myFragment);

        //每次显示都会把显示的fragment存到mCurrent中去
        mCurrent = homeFragment;
    }


    //保存回收前的tab
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存回收前的tab
        outState.putString(SAVE_CURRENT_FRAGMENT_TAG, mCurrent.getClass().getSimpleName());
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.ic_home:
                mCurrent = homeFragment;
                getSupportFragmentManager().beginTransaction()
                        .show(homeFragment)
                        .hide(orderFragment)
                        .hide(myFragment)
                        .commit();
                break;
            case R.id.ic_order:
                mCurrent = orderFragment;
                getSupportFragmentManager().beginTransaction()
                        .hide(homeFragment)
                        .show(orderFragment)
                        .hide(myFragment)
                        .commit();
                break;
            case R.id.ic_mine:
                mCurrent = myFragment;
                getSupportFragmentManager().beginTransaction()
                        .hide(homeFragment)
                        .hide(orderFragment)
                        .show(myFragment)
                        .commit();
                break;
        }
        return true;
    }
}
