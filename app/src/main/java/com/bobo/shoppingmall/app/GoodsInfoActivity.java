package com.bobo.shoppingmall.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.home.bean.GoodsBean;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;
import com.bumptech.glide.Glide;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.bobo.shoppingmall.home.adapter.HomeFragmentAdapter.GOODS_BEAN;
import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

public class GoodsInfoActivity extends Activity {

    /**商品详情(本页面)数据对象*/
    private GoodsBean goodsBean;

    @Bind(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @Bind(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @Bind(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @Bind(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @Bind(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @Bind(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @Bind(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @Bind(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @Bind(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @Bind(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @Bind(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @Bind(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @Bind(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @Bind(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @Bind(R.id.tv_more_share)
    TextView tvMoreShare;
    @Bind(R.id.tv_more_search)
    TextView tvMoreSearch;
    @Bind(R.id.tv_more_home)
    TextView tvMoreHome;
    @Bind(R.id.btn_more)
    Button btnMore;
    @Bind(R.id.activity_goods_info)
    LinearLayout activityGoodsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);

        //美化状态栏
        statusBarSystemSet();

        //接收数据
        goodsBean = (GoodsBean)getIntent().getSerializableExtra(GOODS_BEAN);

        if (goodsBean != null){
            //设置数据
            setDataForView(goodsBean);
        }

    }

    private void setDataForView(GoodsBean goodsBean){

        //设置图片
        Glide.with(GoodsInfoActivity.this).load(BASE_URL_IMAGE+goodsBean.getFigure())
                .placeholder(R.drawable.occupation).into(ivGoodInfoImage);

        //设置文本(名称)
        tvGoodInfoName.setText(goodsBean.getName());

        //设置文本(价格)
        tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());

        setWebViewData(goodsBean.getProduct_id());
    }

    private void setWebViewData(String product_id) {

        if (product_id != null){

         //http://www.atguigu.com
         wbGoodInfoMore.loadUrl("https://github.com/leonInShanghai/ShoppingMall/blob/master" +
                 "/README.md");

         WebSettings webSettings = wbGoodInfoMore.getSettings();

         //设置允许执行JavaScript
         webSettings.setJavaScriptEnabled(true);

         //设置允许双击变大变小
         webSettings.setUseWideViewPort(true);

         //设置优先使用缓存
         webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

         // 重定向解決方法: mWebView.setWebViewClient(new WebViewClient());
         wbGoodInfoMore.setWebViewClient(new WebViewClient());

         // 下面这种写法解决重定向阿福老师写的有问题
//         wbGoodInfoMore.setWebViewClient(new WebViewClient(){
//
//             //低版本手机调用这个方法
//             @Override
//             public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                 //返回值时 true的时候 控制去webview打开,为false的时候调用系统浏览器或第三方浏览器
//                 view.loadUrl(url);
//                 return true;
//             }
//
//             //高版本的安卓手机会调用这个方法
//             @Override
//             public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                     view.loadUrl(request.getUrl().toString());
//                 }
//                 return true;
//             }
//         });
        }
    }

    //对不同的安卓手机状态栏透明 字体颜色为黑色处理
    private void statusBarSystemSet() {

        //动态计算statusBar高度
        StBarUtil.setOccupationHeight(this, null);

        //设置状态栏上的字体为黑色（OV系手机的状态栏字体是白色）-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(this, true);
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.iv_good_info_image, R.id.
            tv_good_info_name, R.id.tv_good_info_desc, R.id.tv_good_info_price, R.id.
            tv_good_info_store, R.id.tv_good_info_style, R.id.wb_good_info_more, R.id.
            tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart,
            R.id.btn_good_info_addcart, R.id.ll_goods_root, R.id.tv_more_share, R.id.tv_more_search,
            R.id.tv_more_home, R.id.btn_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                //用户点击了左上角的返回按钮
                finish();
                break;
            case R.id.ib_good_info_more:
                //用户点击了更多
                Toast.makeText(GoodsInfoActivity.this,"Goods2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_good_info_image:
                Toast.makeText(GoodsInfoActivity.this,"Goods3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_name:
                Toast.makeText(GoodsInfoActivity.this,"Goods4",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_desc:
                Toast.makeText(GoodsInfoActivity.this,"Goods5",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_price:
                Toast.makeText(GoodsInfoActivity.this,"Goods6",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_store:
                Toast.makeText(GoodsInfoActivity.this,"Goods7",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_style:
                Toast.makeText(GoodsInfoActivity.this,"Goods8",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wb_good_info_more:
                Toast.makeText(GoodsInfoActivity.this,"Goods9",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_callcenter:
                //用户点击了联系客服
                Toast.makeText(GoodsInfoActivity.this,"Goods10",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_collection:
                //用户点击了收藏
                Toast.makeText(GoodsInfoActivity.this,"Goods11",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
                //用户点击了购物车
                Toast.makeText(GoodsInfoActivity.this,"Goods12",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_good_info_addcart:
                //用户点击了添加到购物车
                Toast.makeText(GoodsInfoActivity.this,"添加到购物车",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_goods_root:
                Toast.makeText(GoodsInfoActivity.this,"Goods14",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_share:
                Toast.makeText(GoodsInfoActivity.this,"Goods15",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(GoodsInfoActivity.this,"Goods16",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(GoodsInfoActivity.this,"Goods17",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_more:
                Toast.makeText(GoodsInfoActivity.this,"Goods18",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        //這裏解決本頁面finish后音樂還在播放的問題
        //mWebView.destroy();

        ///销毁webview比较正规的写法
        if (wbGoodInfoMore != null) {
            wbGoodInfoMore.loadDataWithBaseURL(null, "", "text/html",
                    "utf-8", null);
            wbGoodInfoMore.clearHistory();

            ((ViewGroup) wbGoodInfoMore.getParent()).removeView(wbGoodInfoMore);
            wbGoodInfoMore.destroy();
            wbGoodInfoMore = null;
        }

        super.onDestroy();
    }
}
