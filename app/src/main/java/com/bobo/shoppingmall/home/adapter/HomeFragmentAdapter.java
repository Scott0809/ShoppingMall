package com.bobo.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.bobo.shoppingmall.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo.shoppingmall.app.GoodsInfoActivity;
import com.bobo.shoppingmall.home.bean.ResultBeanData;
import com.bobo.shoppingmall.magicviewpager.ScaleInTransformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

/**
 * Created by 求知自学网 on 2019/5/12. Copyright © Leon. All rights reserved.
 * Functions: 首页RecyclerView 的适配器
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter {

    /**广告条幅类型*/
    public static final int BANNER = 0;

    /**频道类型*/
    public static final int CHANNEL = 1;

    /**活动类型*/
    public static final int ACT = 2;

    /**秒杀类型*/
    public static final int SECKILL = 3;

    /**推荐类型*/
    public static final int RECOMMEND = 4;

    /**热卖类型*/
    public static final int HOT = 5;

    /**当前类型默认是0*/
    private int currentType = BANNER;

    private Context mContext;
    //数据对象
    private ResultBeanData.ResultBean resultBean;

    //用LayoutInflater初始化布局 和 View.inflate()类似
    private LayoutInflater mLayoutInflater;

    public HomeFragmentAdapter(Context context, ResultBeanData.ResultBean resultBean) {
        this.mContext = context;
        this.resultBean = resultBean;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于BaseAdapter的getView方法 创建ViewHolder部分代码
     * 创建viewHolder
     * @param viewGroup
     * @param viewType 当前的类型
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == BANNER){//创建处理banner类型 ViewHolder
            return new BannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.banner_viewpager,
                    null));
        }else if (viewType == CHANNEL){//创建频道类型ViewHolder
            return new ChannelViewHolder(mContext,mLayoutInflater.inflate(R.layout.channel_item,
                    null));
        }else if (viewType == ACT){//创建活动类型ViewHolder
            return new ActViewHolder(mContext,mLayoutInflater.inflate(R.layout.act_item,
                    null));
        }else if (viewType == SECKILL){//创建秒杀类型ViewHolder
            return new SeckillViewHolder(mContext,mLayoutInflater.inflate(R.layout.seckill_item,
                    null));
        }else if (viewType == RECOMMEND){//创建推荐类型ViewHolder
            return new RecommendViewHolder(mContext,mLayoutInflater.inflate(R.layout.recommend_item,
                    null));
        }else if (viewType == HOT){//创建推荐类型ViewHolder
            return new HotViewHolder(mContext,mLayoutInflater.inflate(R.layout.hot_item,
                    null));
        }
        return null;
    }

    /**
     * 相当于BaseAdapter的getView方法中的绑定数据模块
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            if (getItemViewType(position) == BANNER){
                BannerViewHolder bannerViewHolder = (BannerViewHolder)viewHolder;
                //开始设置数据
                bannerViewHolder.setData(resultBean.getBanner_info());
            }else if (getItemViewType(position) == CHANNEL){
                ChannelViewHolder channelViewHolder = (ChannelViewHolder)viewHolder;
                //开始设置数据
                channelViewHolder.setData(resultBean.getChannel_info());
            }else if (getItemViewType(position) == ACT){
                ActViewHolder actViewHolder = (ActViewHolder)viewHolder;
                //开始设置数据
                actViewHolder.setData(resultBean.getAct_info());
            }else if (getItemViewType(position) == SECKILL){
                SeckillViewHolder seckillViewHolder = (SeckillViewHolder)viewHolder;
                //开始设置数据
                seckillViewHolder.setData(resultBean.getSeckill_info());
            }else if (getItemViewType(position) == RECOMMEND){
                RecommendViewHolder recommendViewHolder = (RecommendViewHolder)viewHolder;
                //开始设置数据
                recommendViewHolder.setData(resultBean.getRecommend_info());
            }else if (getItemViewType(position) == HOT){
                HotViewHolder hotViewHolder = (HotViewHolder)viewHolder;
                //开始设置数据
                hotViewHolder.setData(resultBean.getHot_info());
            }
    }

    //热销类型的ViewHolder 注意要继承RecyclerView.ViewHolder
    class HotViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        //查看更多
        private TextView tv_more_hot;

        //展示内容的 GridView
        private GridView gv_hot;

        private HotGridViewAdapter adapter;

        public HotViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.tv_more_hot = (TextView)itemView.findViewById(R.id.tv_more_hot);
            this.gv_hot = (GridView)itemView.findViewById(R.id.gv_hot);
            //设置item的点击事件监听
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(mContext,"热销"+position,Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            //1.有数据2.设置gridview适配器
            adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);
        }
    }

    //推荐类型的ViewHolder 注意要继承RecyclerView.ViewHolder
    class RecommendViewHolder extends RecyclerView.ViewHolder{

        private Context mConext;

        //查看更多
        private TextView tv_more_recommend;

        //展示内容的gridview
        private GridView gv_recommend;

        private RecommendGridViewAdapter adapter;


        public RecommendViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mConext = mContext;
            this.tv_more_recommend = (TextView)itemView.findViewById(R.id.tv_more_recommend);
            this.gv_recommend = (GridView)itemView.findViewById(R.id.gv_recommend);

            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //1.有数据了2.设置适配器
            adapter = new RecommendGridViewAdapter(mConext,recommend_info);
            gv_recommend.setAdapter(adapter);
        }
    }

    //秒杀类型的viewholder 注意要继承RecyclerView.ViewHolder
    class SeckillViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;

        //倒计时 文本
        private TextView tv_time_sekill;

        //最后面的点击更多
        private TextView tv_more_seckill;

        //横向滚动的RecyclerView
        private RecyclerView rv_seckill;

        private SeckillRecyclerViewAdapter adapter;

        /**秒杀：相差多少时间-单位是毫秒*/
        private long dt = 0;

        /**用作倒计时的handler*/
        private Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt -= 1000;
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(new Date(dt));
                tv_time_sekill.setText(time);

                //移除消息
                handler.removeMessages(0);
                //再发消息
                handler.sendEmptyMessageDelayed(0,1000);
                if (dt <= 0){
                    //把消息移除
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_time_sekill = (TextView)itemView.findViewById(R.id.tv_time_sekill);
            tv_more_seckill = (TextView)itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView)itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {

            //1.得到数据了 2.设置数据：文本和RecyclerView的数据
            adapter = new SeckillRecyclerViewAdapter(mContext,seckill_info.getList());
            rv_seckill.setAdapter(adapter);

            //设置recycview的布局管理器 LinearLayoutManager.HORIZONTAL 横向
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,
                    false));

            //设置item的点击事件
            adapter.setOnSeckillRecycleView(new SeckillRecyclerViewAdapter.OnSeckillRecycleView() {
                @Override
                public void onItemClick(int position) {
                    //Toast.makeText(mContext,"秒杀"+position,Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });

            //秒杀倒计时 - 毫秒
            dt = Long.valueOf(seckill_info.getEnd_time()) - Long.valueOf(seckill_info.getStart_time());
            handler.sendEmptyMessageDelayed(0,1000);
        }
    }

    //活动类型的viewholder 注意要继承RecyclerView.ViewHolder
    class ActViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.act_viewpager = (ViewPager)itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info){

            //设置间距
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);//>=3

            //setPageTransformer 决定动画效果
            act_viewpager.setPageTransformer(true, new ScaleInTransformer());

            //给ViewPager 设置适配器
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {

                    //return act_info == null ? 0 : act_info.size();

                    if (act_info == null ) {
                        return 0;//避免空指针
                    }else if (act_info.size() == 1){
                        return act_info.size();// 一张图片时不用流动
                    }

                    //这样写是为了让banner图一直可以自动滚动-注意适配器其他方法中的position一定要取余
                    // 避免数组越界
                    return Integer.MAX_VALUE;
                }

                /**
                 * @param view 页面
                 * @param object instantiateItem 方法返回的值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                /**
                 * @param container  view pager 自己
                 * @param position 对应页面的位置
                 * @return
                 */
                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container,int position) {

                    //2019-5-20 解决左划白屏的bug ↓
                    position = position % act_info.size();
                    if (position < 0) {
                        position =  act_info.size() + position;
                    }
                    //2019-5-20 解决左划白屏的bug ↑

                    final ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    //----------------------------------动态计算最佳宽高↓----------------------------
                    //动态的计算出 act_viewpager 的宽度
                    int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    //注意这里写act_viewpager 会 java.lang.Stack Over flow Error: stack size 8MB
                    // 有的报错信息后来才写了imageview 貌似也能动态计算最佳宽高先这样
                    imageView.measure(w, h);
                    final int actW = act_viewpager.getMeasuredWidth();//这里得出的宽度单位是px
                    //int actW = DensityUtil.px2dip(mContext,(float)act_viewpager.getMeasuredWidth());

                    String url = BASE_URL_IMAGE+act_info.get(position).getIcon_url();

                    //获取图片真正的宽高
                    Glide.with(mContext).load(url).into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super
                                GlideDrawable> glideAnimation) {
                            //Intrinsic固有的，内在的，本质的
                            int widthImg = glideDrawable.getIntrinsicWidth();
                            int heightImg = glideDrawable.getIntrinsicHeight();

                            // 控件的宽度（屏幕宽度-左右边距）actW * （图片高 ÷ 图片宽）
                            ViewGroup.LayoutParams params = act_viewpager.getLayoutParams();

                            //Math.round 函数返回一个数字四舍五入后最接近的整数
                            params.height  = Math.round(actW * (float)(heightImg / (float)widthImg));
                            //int tW =  DensityUtil.px2dip(mContext,(float)params.height); 120dp
                            act_viewpager.setLayoutParams(params);
                        }
                    });
                    //----------------------------------动态计算最佳宽高↑----------------------------

                    Glide.with(mContext).load(BASE_URL_IMAGE+act_info.get(position).getIcon_url())
                            .placeholder(R.drawable.occupation).into(imageView);
                    //添加到容器中
                    container.addView(imageView);

                    //设置点击事件
                    final int finalPosition = position;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext,"点击了"+ finalPosition,Toast.LENGTH_SHORT).show();
                        }
                    });

                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object
                        object) {

                    //警告:为了实现imageview 轮播图无限往一个方向滑动 不要在这里调用removeView
                    //container.removeView((View)object);

                    //2019-5-19 解决左划白屏的问题↓
                    container.removeView((View)object);
                    if (object != null){
                        object = null;
                    }
                }
            });

            /**
             * 设置view pager的初始位置从max value的中间开始 实现用户可以左划banner
             * 注意：①细节并不是从第一张图片开始展示 需要处理一下
             * 注意：② 放在setAdapter后面才起作用
             */
            if (act_info.size() > 1){
                act_viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % act_info.size());
            }else {
                //只有一张图就不要滚动了
                act_viewpager.setCurrentItem(0);
            }
        }
    }

    //频道类型的viewholder 注意要继承RecyclerView.ViewHolder
    class ChannelViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter channelAdapter;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            this.gv_channel = (GridView)itemView.findViewById(R.id.gv_channel);

            //设置本item中的GridView 的item的点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"点击了频道的"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            //得到数据了要去设置GridView 的适配器 - 创建适配器
            channelAdapter = new ChannelAdapter(mContext,channel_info);

            //设置适配器
            gv_channel.setAdapter(channelAdapter);
        }
    }

    //Banner类型的 ViewHolder 注意要继承RecyclerView.ViewHolder
    class BannerViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private Banner banner;


        public BannerViewHolder(Context context, View itemView) {
           super(itemView);
           this.mContext = context;
           //网络上请求的图片宽高比为 690 ： 300
           this.banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {

            //动态设置banner的最佳高度 使得图片不会被拉伸变形
            //Math.round 函数返回一个数字四舍五入后最接近的整数
            int viewHeight = Math.round(getScreenWidth() * 300 / 690);
            //int dip =  DensityUtil.px2dip(mContext,viewHeight);//viewHeight == 156dp
            //设置Banner的最佳高度
            ViewGroup.LayoutParams banner_params = banner.getLayoutParams();
            banner_params.height = viewHeight;
            banner.setLayoutParams(banner_params);

            //设置banner的数据 - 得到图片集合地址
            List<String> imagesUrl = new ArrayList<>();

            for (int i = 0;i < banner_info.size();i++){
                String imageUrl = banner_info.get(i).getImage();
                imagesUrl.add(imageUrl);
            }

            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());

            //设置图片集合
            banner.setImages(imagesUrl);

            //设置banner动画效果
            //banner.setBannerAnimation(Transformer.DepthPage);//深度页 动画效果
            banner.setBannerAnimation(Transformer.Accordion);//手风琴效果

            //banner设置方法全部调用完毕时最后调用
            banner.start();

            //banner点击事件的监听 Deprecated：banner.setOnBannerClickListener();
            banner.setOnBannerListener(new OnBannerListener(){

                @Override
                public void OnBannerClick(int position) {
                    //Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });
        }
    }

    /**启动商品信息列表页面 跳转到商品详情页的方法*/
    private void startGoodsInfoActivity(){
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 根据索引的到item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        switch (position){
            case BANNER:
                currentType  = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 总共有多少个item
     * @return
     */
    @Override
    public int getItemCount() {

        //开发过程中从1-->6
        return 6;
    }

    // banner 类型item用到.重写图片加载器
    class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            path = BASE_URL_IMAGE + path;
            Glide.with(context).load(path).into(imageView);
        }

    }

    //获取屏幕的宽度
    private int getScreenWidth(){

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return  size.x;
    }

}



// step4.重写图片加载器
//class GlideImageLoader extends ImageLoader {
//    @Override
//    public void displayImage(Context context, Object path, ImageView imageView) {
//        /**
//         注意：
//         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
//         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
//         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
//         切记不要胡乱强转！
//         */
//
//        //Glide 加载图片简单用法
//        Glide.with(context).load(path).into(imageView);
//
//        //Picasso 加载图片简单用法
//        Picasso.with(context).load(path).into(imageView);
//
//        //用fresco加载图片简单用法，记得要写下面的createImageView方法
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
//    }
//
//    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//    @Override
//    public ImageView createImageView(Context context) {
//        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//        return simpleDraweeView;
//    }
//}