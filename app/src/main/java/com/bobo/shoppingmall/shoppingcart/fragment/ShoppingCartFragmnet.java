package com.bobo.shoppingmall.shoppingcart.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.home.bean.GoodsBean;
import com.bobo.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.bobo.shoppingmall.shoppingcart.pay.PayResult;
import com.bobo.shoppingmall.shoppingcart.pay.SignUtils;
import com.bobo.shoppingmall.shoppingcart.utils.CartStorage;
import com.bobo.shoppingmall.utils.LELog;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
    public void onResume() {
        super.onResume();
        //解决页面切换数据不刷新的bug
        showData();
        //校验 编辑textView的状态 点击取反 用户删完购物车后应为编辑状态
        //checkEditTag();
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
                //用户点击了结算按钮-pay(view) 支付宝dome中的支付方法
                pay(view);
                Toast.makeText(mContext,"点击了结算按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                //用户点击了删除按钮-删除选中的item 校验状态（如果全部被删光了全选按钮要恢复到未选中）
                adapter.deleteData();

                //校验状态（如果全部被删光了全选按钮要恢复到未选中 chackAll 中的大else 有处理）
                adapter.checkAll();

                //判断时候删除了所有商品 如果删除了所有商品就显示 空的页面
                if (adapter.getItemCount() == 0){
                    //显示空的页面
                    emptyShoppingCart();
                }

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
               // int action = (int)view.getTag();
                checkEditTag();
                break;
        }
    }

    //校验 编辑textView的状态 点击取反 用户删完购物车后应为编辑状态
    private void checkEditTag() {
        int action = (int)tvShopcartEdit.getTag();
        //判断是否是编辑状态
        if (action == ACTION_EDIT){
            //切换为完成状态
            showDelete();
        }else if (action == ACTION_COMPLETE){//是完成状态
            //切换到编辑状态
            hideDelete();
        }
    }

    private void hideDelete(){
        //1.设置状态和文本-编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");

        //2.全部变成非勾选
        if (adapter != null){
            //所有的item都设为已勾选
            adapter.checkAll_none(true);
            //Leon添加原来的代码写的不够严谨
            adapter.checkAll();
            adapter.showTotalPrice();
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

        //2.全部变成非勾选
        if (adapter != null){
            //所有的item都设为非勾选
            adapter.checkAll_none(false);
            adapter.checkAll();
        }

        //3.删除视图显示
        llDelete.setVisibility(View.VISIBLE);

        //4.结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();

        //放在的onResume方法中
        //showData();
    }

    /**显示数据*/
    private void showData() {

        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();

        if (goodsBeanList != null && goodsBeanList.size() > 0){

            //显示底部的结算布局
            llCheckAll.setVisibility(View.VISIBLE);

            //有数据右上角的 编辑/完成 显示
            tvShopcartEdit.setVisibility(View.VISIBLE);

            //有数据-把没有数据显示的布局隐藏
            llEmptyShopcart.setVisibility(View.GONE);

            //设置适配器
            adapter = new ShoppingCartAdapter(mContext,goodsBeanList,tvShopcartTotal,checkboxAll,cbAll);
            recyclerview.setAdapter(adapter);

            //设置布局管理器  recycleview 一定要是设置否者不显示
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,
                    false));
        }else{
            //没有数据-显示数据为空的布局
            emptyShoppingCart();
        }
    }

    //没有数据-显示数据为空的布局
    private void emptyShoppingCart() {
        llEmptyShopcart.setVisibility(View.VISIBLE);
        //隐藏顶部布局右边的 编辑
        tvShopcartEdit.setVisibility(View.GONE);
        //隐藏底部的 结算布局 编辑（删除） 和完成 2种状态 结算布局
        llDelete.setVisibility(View.GONE);//完成 状态下的 结算布局
        //llCheckAll.setVisibility(View.GONE);//编辑（删除）状态 下的 结算布局
        //右上角的 编辑/完成 textView的状态也应该切换为编辑 因为数据都删完了 不要再进删除模式了
        hideDelete();
    }

    //对不同的安卓手机状态栏透明 字体颜色为黑色处理
    private void statusBarSystemSet(View view) {

        //动态计算statusBar高度 交给各个fragmnet实现因为各个fragment的状态栏颜色不一样
        StBarUtil.setOccupationHeight(getActivity(),view);

        //设置状态栏上的字体为黑色（OV系手机的状态栏字体是白色）-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(getActivity(),true);
    }


    ///////////////////////////////////////////////支付宝集成/////////////////////////////////////////////

    // 商户PID
    public static final String PARTNER = "";
    // 商户收款账号
    public static final String SELLER = "";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";
    // 支付宝公钥-注意公钥还要保存在服务器上
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     * 支付宝dome中复制的方法
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo("波波商城商品", "波波商城的信息描述",
                adapter.getTotalPrice()+"");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
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