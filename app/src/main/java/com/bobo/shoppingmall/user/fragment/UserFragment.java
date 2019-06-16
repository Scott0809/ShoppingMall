package com.bobo.shoppingmall.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.user.activity.MessageCenterActivity;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 用户页面的fragmengt
 */
public class UserFragment extends BaseFragment {

    @Bind(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_all_order)
    TextView tvAllOrder;
    @Bind(R.id.tv_user_pay)
    TextView tvUserPay;
    @Bind(R.id.tv_user_receive)
    TextView tvUserReceive;
    @Bind(R.id.tv_user_finish)
    TextView tvUserFinish;
    @Bind(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @Bind(R.id.tv_user_location)
    TextView tvUserLocation;
    @Bind(R.id.tv_user_collect)
    TextView tvUserCollect;
    @Bind(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @Bind(R.id.tv_user_score)
    TextView tvUserScore;
    @Bind(R.id.tv_user_prize)
    TextView tvUserPrize;
    @Bind(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @Bind(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @Bind(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @Bind(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @Bind(R.id.scrollview)
    ScrollView scrollview;
    @Bind(R.id.tv_usercenter)
    TextView tvUsercenter;
    @Bind(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @Bind(R.id.ib_user_message)
    ImageButton ibUserMessage;
    private TextView textView;


    @Override
    public void onResume() {
        super.onResume();
        if (tvUsercenter != null){
            tvUsercenter.setAlpha(0);
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_user, null);

        //美化状态栏
        statusBarSystemSet(view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        /**
         * 用户滚动scrollview顶部的状态栏颜色渐变 就像我当年处理畅学网校那样
         */
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ib_user_icon_avator, R.id.ib_user_setting, R.id.ib_user_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_user_icon_avator:
                Toast.makeText(mContext, "头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_setting:
                Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_message:
                //用户点击了消息image button
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                startActivity(intent);
                break;
        }
    }

    //对不同的安卓手机状态栏透明 字体颜色为黑色处理
    private void statusBarSystemSet(View view) {

        //动态计算statusBar高度 交给各个fragmnet实现因为各个fragment的状态栏颜色不一样
        StBarUtil.setOccupationHeight(getActivity(),view);

        //设置状态栏上的字体为黑色（OV系手机的状态栏字体是白色）-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(getActivity(),true);
    }
}
