package com.wuxiankeneng.jian.fragment.main;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.app.BaseFragment;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.Factory;
import com.wuxiankeneng.factory.model.MyItemModel;
import com.wuxiankeneng.factory.presenter.Account;
import com.wuxiankeneng.jian.MainActivity;
import com.wuxiankeneng.jian.R;
import com.wuxiankeneng.jian.activity.AccountActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends BaseFragment
        implements AppBarLayout.OnOffsetChangedListener, RecyclerAdapter.AdapterListener<MyItemModel> {
    @BindView(R.id.im_portrait)
    CircleImageView mPortrait;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.view_collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private MenuItem mSettingMenuItem;

    private RecyclerAdapter<MyItemModel> adapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Toolbar toolbar = mToolbar;
        toolbar.inflateMenu(R.menu.menu_setting);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.set_menu) {
                if (!Account.isLogin())
                    login();
                else
                    Application.showToast("已经登陆");
            }
            return false;
        });
        //把menu拿出来  配合折叠布局显示吟唱
        mSettingMenuItem = toolbar.getMenu().findItem(R.id.set_menu);
        appbar.addOnOffsetChangedListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(adapter = new RecyclerAdapter<MyItemModel>() {
            @Override
            protected int getItemViewType(int position, MyItemModel myItemModel) {
                return R.layout.item_my;
            }

            @Override
            protected ViewHolder<MyItemModel> onCreateViewHolder(View view, int viewType) {
                return new MyFragment.ViewHolder(view);
            }
        });

//        //是否登陆
        if (Account.isLogin()) {
            //登陆就显示保存到xml文件的信息
            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(Account.getUser().getPortrait())
                    .placeholder(R.drawable.default_portrait)
                    .dontAnimate()
                    .into(mPortrait);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        List<MyItemModel> dataList = new ArrayList<>();
        dataList.add(new MyItemModel(getResources().getDrawable(R.drawable.ic_address, null), "地址管理"));
        adapter.add(dataList);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.im_portrait)
    public void login() {
        //转到登陆界面
        if (Account.isLogin())
            return;
        startActivityForResult(new Intent(getContext(), AccountActivity.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 0) {
            //进行更新用户信息
            //是否登陆
            if (Account.isLogin()) {
                //登陆就显示保存到xml文件的信息
                Glide.with(Objects.requireNonNull(getActivity()))
                        .load(Account.getUser().getPortrait())
                        .placeholder(R.drawable.default_portrait)
                        .dontAnimate()
                        .into(mPortrait);
            }
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        verticalOffset = Math.abs(verticalOffset);
        if (verticalOffset >= appBarLayout.getTotalScrollRange()) {
            //完全收起  显示menu
            mSettingMenuItem.setVisible(true);
            collapsingToolbarLayout.setTitleEnabled(true);

        } else {
            mSettingMenuItem.setVisible(false);
            collapsingToolbarLayout.setTitleEnabled(false);
        }
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder viewHolder, MyItemModel myItemModel) {

    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder viewHolder, MyItemModel myItemModel) {

    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<MyItemModel> {
        @BindView(R.id.im_address)
        ImageView mAddressImage;
        @BindView(R.id.txt_address)
        TextView mAddressText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MyItemModel myItemModel) {
            mAddressImage.setImageDrawable(myItemModel.getDrawable());
            mAddressText.setText(myItemModel.getName());
        }
    }
}
