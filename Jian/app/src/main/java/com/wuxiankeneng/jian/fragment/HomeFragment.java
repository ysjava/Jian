package com.wuxiankeneng.jian.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuxiankeneng.common.app.BaseFragment;
import com.wuxiankeneng.jian.MainActivity;
import com.wuxiankeneng.jian.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.edt_nav_search)
    EditText editText;
    @BindView(R.id.banner)
    BGABanner mBanner;
    List<View> list;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        list = new ArrayList<>();
        list.add(getLayoutInflater().inflate(R.layout.page_1, null, false));
        list.add(getLayoutInflater().inflate(R.layout.page_2, null, false));
        list.add(getLayoutInflater().inflate(R.layout.page_3, null, false));

        mBanner.setData(list, Arrays.asList("https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/baiju.jpeg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/dinxianghua.jpg",
                "https://italker-im-new.oss-cn-hongkong.aliyuncs.com/huadian/lanju.jpg"),
                Collections.singletonList(""));

        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                ImageView imageView = itemView.findViewById(R.id.iv_pager);
                Glide.with(getContext())
                        .load(model)
                        .dontAnimate()
                        .centerCrop()
                        .into(imageView);
            }
        });
    }

    @OnClick(R.id.edt_nav_search)
    public void testClick() {
        Toast.makeText(getContext(), "NNN", Toast.LENGTH_LONG).show();
    }
}
