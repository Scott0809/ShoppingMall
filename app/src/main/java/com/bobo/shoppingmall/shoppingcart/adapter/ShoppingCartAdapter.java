package com.bobo.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.shoppingmall.home.bean.GoodsBean;

import java.util.List;
import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.shoppingcart.utils.CartStorage;
import com.bobo.shoppingmall.shoppingcart.view.AddSubView;
import com.bumptech.glide.Glide;
import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

/**
 * Created by 求知自学网 on 2019/6/9. Copyright © Leon. All rights reserved.
 * Functions: 购物车中recycleview的适配器
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private Context mContext;

    private List<GoodsBean> datas;

    //从fragment中传递过来的总价格text文本框
    private TextView tvShopcartTotal;

    //从fragment中传递过来的是否选中全部选择框
    private CheckBox checkboxAll;

    /**由于recycleview 没有item的点击事件监听在这回事 自定义接口传递点击事件*/
    private OnItemClickLinstener onItemClickLinstener;


    public ShoppingCartAdapter(Context context, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal,
                               CheckBox checkboxAll) {
        this.mContext = context;
        this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;

        //计算总价格
        showTotalPrice();

        //设置点击事件
        setListenner();

        //校验是否全选
        checkAll();
    }

    //设置点击事件
    private void setListenner(){

        //这里的接口一般定义给外界使用的 这里在本类中自己使用了
        setOnItemClickLinstener(new OnItemClickLinstener() {
            @Override
            public void OnItemClick(int position) {
                //根据位置找到对应的bean对象
                GoodsBean goodsBean = datas.get(position);

                //2.设置取反状态 isSelscted
                goodsBean.setSelected(!goodsBean.isSelected());

                //3.刷新状态(刷新一条item)
                notifyItemChanged(position);

                //4.校验是否全选
                checkAll();

                //5.重新计算总价格
                showTotalPrice();
            }
        });

        //checkboxAll 设置全选的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();

                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);

                //3.重新计算总价格
                showTotalPrice();

            }
        });
    }

    /**设置全选/非全选*/
    public void checkAll_none(boolean isCheck){
        if (datas != null && datas.size() > 0){
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                //数据刷新 每个item 一条一条刷新
                notifyItemChanged(i);
            }
        }
    }

    //4.校验是否全选
    private void checkAll(){
        if (datas != null && datas.size() > 0){

            int number = 0;

            for (int i = 0;i < datas.size();i++){
                GoodsBean goodsBean = datas.get(i);

                //只要有一个没有被选中就是 未全选
                if (!goodsBean.isSelected()){
                    //非全选
                    checkboxAll.setChecked(false);
                }else{
                    //选中的
                    number++;
                }
            }

            if (number == datas.size()){
                //全选
                checkboxAll.setChecked(true);
            }
        }
    }

    //显示总价格
    private void  showTotalPrice(){
        tvShopcartTotal.setText("￥"+getTotalPrice());
    }


    /**计算总价格的方法*/
    private double getTotalPrice(){

        double totalPrice = 0.0;

        //如果购物车中有商品
        if (datas != null && datas.size() > 0){

            //遍历集合中的所有商品做价格的计算
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);

                //只计算用户选择的商品
                if (goodsBean.isSelected()){

                    //总价格 ==  原来的总价格 + 商品的个数 * 商品的价格
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) *
                            Double.valueOf(goodsBean.getCover_price()) ;

                }
            }
        }

        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = View.inflate(mContext,R.layout.item_shop_cart,null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //1.根据位置得到对应的bean对象
        final GoodsBean goodsBean = datas.get(position);

        //2.设置数据 ①设置是否选中
        holder.cb_gov.setChecked(goodsBean.isSelected());
        //设置购物车中产品的图片
        Glide.with(mContext).load(BASE_URL_IMAGE+goodsBean.getFigure()).placeholder(R.drawable
                .top_btn).into(holder.iv_gov);
        //设置商品的描述信息(名称)
        holder.tv_desc_gov.setText(goodsBean.getName());
        //设置商品的价格
        holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());
        //设置用户选择了多少个商品
        holder.addSubView.setValue(goodsBean.getNumber());
        //设置（购物车）最小值
        holder.addSubView.setMinValue(1);
        //设置（购物车）最大值
        holder.addSubView.setMaxValue(8);

        //设置商品数量的变化 + - 号 点击事件的监听
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.当前列表内存中和
                goodsBean.setNumber(value);

                //2.本地要更新
                CartStorage.getInstance().updateData(goodsBean);

                //3.刷新适配器
                notifyItemChanged(position);

                //4.再次计算总价格
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        //最左边的选择框
        private CheckBox cb_gov;

        //商品图片
        private ImageView iv_gov;

        //商品描述
        private TextView tv_desc_gov;

        //商品价格
        private TextView tv_price_gov;

        //增加减少按钮
        private AddSubView addSubView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = (CheckBox)itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView)itemView.findViewById(R.id.iv_gov);
            tv_desc_gov =(TextView)itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView)itemView.findViewById(R.id.tv_price_gov);
            addSubView = (AddSubView)itemView.findViewById(R.id.addSubView);

            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickLinstener != null){
                        onItemClickLinstener.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**由于recycleview 没有item的点击事件监听在这回事 自定义接口传递点击事件*/
    public interface OnItemClickLinstener{

        //当点击某一条的时候被回调
        public void OnItemClick(int position);
    }

    /**设置item的监听 点击事件回调接口*/
    public void setOnItemClickLinstener(OnItemClickLinstener onItemClickLinstener) {
        this.onItemClickLinstener = onItemClickLinstener;
    }
}
