package com.bobo.shoppingmall.shoppingcart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.home.bean.GoodsBean;
import com.bobo.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.bobo.shoppingmall.shoppingcart.utils.CartStorage;
import com.bobo.shoppingmall.utils.LELog;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 购物车页面的fragmengt
 */
public class ShoppingCartFragmnet extends BaseFragment {

    @Bind(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.checkbox_all)
    CheckBox checkboxAll;
    @Bind(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @Bind(R.id.btn_check_out)
    Button btnCheckOut;
    @Bind(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @Bind(R.id.cb_all)
    CheckBox cbAll;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_collection)
    Button btnCollection;
    @Bind(R.id.ll_delete)
    LinearLayout llDelete;
    @Bind(R.id.iv_empty)
    ImageView ivEmpty;
    @Bind(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @Bind(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;


    /**本页面recycleview的适配器*/
    private ShoppingCartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;

    //完成状态
    private static final int ACTION_COMPLETE = 2;


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {

        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);

        //美化状态栏
        statusBarSystemSet(view);

        return view;
    }

    private void initListener() {
        //设置默认的编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initListener();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_check_out, R.id.btn_delete, R.id.btn_collection,R.id.iv_empty,
            R.id.tv_empty_cart_tobuy,R.id.tv_shopcart_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_out:
                break;
            case R.id.btn_delete:
                break;
            case R.id.btn_collection:
                break;
            case R.id.iv_empty:
                //用户点击了空页面的logo

                break;
            case R.id.tv_empty_cart_tobuy:
                //用户点击了空页面的textview文本去逛逛

                break;
            case R.id.tv_shopcart_edit:
                //用户点击了右上角的编辑
                int action = (int)view.getTag();

                //判断是否是编辑状态
                if (action == ACTION_EDIT){
                    //切换为完成状态
                    showDelete();
                }else if (action == ACTION_COMPLETE){//是完成状态
                    //切换到编辑状态
                    hideDelete();
                }
                break;
        }
    }

    private void hideDelete(){
        //1.设置状态和文本-编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");

        //2.全部编程非勾选
        if (adapter != null){
            //说有的item都设为已勾选
            adapter.checkAll_none(true);
        }

        //3.删除视图隐藏
        llDelete.setVisibility(View.GONE);

        //4.结算视图显示
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void showDelete(){
        //1.设置状态和文本-完成状态
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");

        //2.全部编程非勾选
        if (adapter != null){
            //说有的item都设为非勾选
            adapter.checkAll_none(false);
        }

        //3.删除视图显示
        llDelete.setVisibility(View.VISIBLE);

        //4.结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();

        showData();
    }

    /**显示数据*/
    private void showData() {

        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();

        if (goodsBeanList != null && goodsBeanList.size() > 0){
            //有数据-把没有数据显示的布局隐藏
            llEmptyShopcart.setVisibility(View.GONE);

            //设置适配器
            adapter = new ShoppingCartAdapter(mContext,goodsBeanList,tvShopcartTotal,checkboxAll);
            recyclerview.setAdapter(adapter);

            //设置布局管理器  recycleview 一定要是设置否者不显示
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,
                    false));


        }else{
            //没有数据-显示数据为空的布局
            llEmptyShopcart.setVisibility(View.VISIBLE);
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


//    private TextView tvShopcartEdit;
//    private RecyclerView recyclerview;
//    private LinearLayout llCheckAll;
//    private CheckBox checkboxAll;
//    private TextView tvShopcartTotal;
//    private Button btnCheckOut;
//    private LinearLayout llDelete;
//    private CheckBox cbAll;
//    private Button btnDelete;
//    private Button btnCollection;
//    private ImageView ivEmpty;
//    private TextView tvEmptyCartTobuy;
//    private LinearLayout ll_empty_shopcart;


// tvShopcartEdit = (TextView)view.findViewById( R.id.tv_shopcart_edit );
//         recyclerview = (RecyclerView)view.findViewById( R.id.recyclerview );
//         llCheckAll = (LinearLayout)view.findViewById( R.id.ll_check_all );
//         checkboxAll = (CheckBox)view.findViewById( R.id.checkbox_all );
//         tvShopcartTotal = (TextView)view.findViewById( R.id.tv_shopcart_total );
//         btnCheckOut = (Button)view.findViewById( R.id.btn_check_out );
//         llDelete = (LinearLayout)view.findViewById( R.id.ll_delete );
//         cbAll = (CheckBox)view.findViewById( R.id.cb_all );
//         btnDelete = (Button)view.findViewById( R.id.btn_delete );
//         btnCollection = (Button)view.findViewById( R.id.btn_collection );
//         ivEmpty = (ImageView)view.findViewById( R.id.iv_empty );
//         tvEmptyCartTobuy = (TextView)view.findViewById( R.id.tv_empty_cart_tobuy );
//         ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
//
//         btnCheckOut.setOnClickListener( this );
//         btnDelete.setOnClickListener( this );
//         btnCollection.setOnClickListener( this );


//    @Override
//    public void onClick(View v) {
//        if ( v == btnCheckOut ) {
//            // Handle clicks for btnCheckOut
//        } else if ( v == btnDelete ) {
//            // Handle clicks for btnDelete
//        } else if ( v == btnCollection ) {
//            // Handle clicks for btnCollection
//        }
//    }