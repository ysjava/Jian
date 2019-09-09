package com.wuxiankeneng.jian.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxiankeneng.common.app.Application;
import com.wuxiankeneng.common.widget.recycler.RecyclerAdapter;
import com.wuxiankeneng.factory.card.AddressCard;
import com.wuxiankeneng.factory.helper.AccountHelper;
import com.wuxiankeneng.factory.presenter.accout.AddressContract;
import com.wuxiankeneng.factory.presenter.accout.AddressPresenter;
import com.wuxiankeneng.jian.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * Created by White paper on 2019/9/4
 * Describe :
 */
public class AddressActivity extends BaseActivityView<AddressPresenter>
        implements AddressContract.View {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.btn_finish)
    Button mFinish;

    private AddressCard card;
    private RecyclerAdapter<AddressCard> adapter;

    private List<ViewHolder> holders = new ArrayList<>();

    public static void show(Context context) {
        ((OrderCommitActivity) context).startActivityForResult(new Intent(context, AddressActivity.class), 1);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Application.showToast("点击");
                if (card == null) {
                    Application.showToast("你还没有选择地址");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("ADDRESS", card);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter = new RecyclerAdapter<AddressCard>() {

            @Override
            protected int getItemViewType(int position, AddressCard addressCard) {
                return R.layout.item_address;
            }

            @Override
            protected ViewHolder<AddressCard> onCreateViewHolder(View view, int viewType) {
                return new AddressActivity.ViewHolder(view);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        //从服务器获取地址
        mPresenter.start();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    public RecyclerAdapter<AddressCard> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    private void selectAddressFinish(AddressCard card, ViewHolder viewHolder) {
        for (ViewHolder holder : holders) {
            if (holder != viewHolder)
                holder.mCheckBox.setChecked(false);
        }

        this.card = card;
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<AddressCard> {
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.txt_phone)
        TextView mPhone;
        @BindView(R.id.txt_address)
        TextView mAddress;
        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        private AddressCard card;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(AddressCard addressCard) {
            this.card = addressCard;
            mName.setText(addressCard.getName());
            mPhone.setText(addressCard.getPhone());
            mAddress.setText(addressCard.getAddress());
        }

        //TODO 逻辑思考
        @OnCheckedChanged(R.id.checkbox)
        void onCheckedChanged(boolean checked) {
            if (checked) {
                //先遍历,把原来选择的地址进行取消
                selectAddressFinish(card, this);
                //添加进地址集合,用于地址勾选的切换
                holders.add(this);
            }
        }
    }

}
