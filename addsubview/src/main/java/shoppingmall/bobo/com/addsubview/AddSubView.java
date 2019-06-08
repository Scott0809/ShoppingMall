package shoppingmall.bobo.com.addsubview;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 求知自学网 on 2019/6/8. Copyright © Leon. All rights reserved.
 * Functions: 自定义的（购物车)删除增加按钮
 */
public class AddSubView extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    /**减少购物车的按钮*/
    private ImageView ivSub;

    /**商品有多少件*/
    private TextView tvValue;

    /**添加购物车的按钮*/
    private ImageView ivAdd;

    /**购物车中的值默认为 1*/
    private int value = 1;

    /**购物车中的最小值 1*/
    private int minValue = 1;

    /**购物车中的最大值 库存的数量*/
    private int maxValue = 5;

    /**自定义接口-供外界知道购物车数据的变化*/
    private OnNumberChangeListener onNumberChangeListener;

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        //通过打气筒加载xml布局 （加载到AddSubView类中） 注意最后一个参数为this
        View.inflate(context, R.layout.add_sub_view, this);

        //打气筒中root不是null是this此时直接findViewById 就可以了
        ivSub = (ImageView)findViewById(R.id.iv_sub);
        tvValue = (TextView)findViewById(R.id.tv_value);
        ivAdd = (ImageView)findViewById(R.id.iv_add);

        //一进来就同步一下购物车
        int value = getValue();
        setValue(value);

        //设置控件的点击事件
        ivSub.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub:
                //用户点击了减少按钮
                subNumber();
                break;
            case R.id.iv_add:
                //用户点击了添加按钮
                addNumber();
                break;
        }

        //Toast.makeText(mContext,"当前的商品数=="+value,Toast.LENGTH_SHORT).show();
    }

    /**购物车中商品增加*/
    private void addNumber(){

        //注意这里只能小于不能等于 等于后+1 就大于库存了
        if (value < maxValue){
            value++;
        }

        // + - 之后都要setvalue
        setValue(value);

        //每次数量发送变化都通过自定义接口传递出去
        if (onNumberChangeListener != null){
            onNumberChangeListener.onNumberChange(value);
        }
    }

    /**购物车中商品减少*/
    private void subNumber(){
        if (value > minValue){
            value--;
        }

        // + - 之后都要setvalue
        setValue(value);

        //每次数量发送变化都通过自定义接口传递出去
        if (onNumberChangeListener != null){
            onNumberChangeListener.onNumberChange(value);
        }
    }

    public int getValue() {

        //获取文本框上购物车的数量 ToString()是转化为字符串的方法 Trim()是去两边空格的方法
        String valueStr = tvValue.getText().toString().trim();

        //这样实时获取ui界面上的 商品数量和用户看到的一致更安全正确
        if (!TextUtils.isEmpty(valueStr)){
            value = Integer.parseInt(valueStr);
        }

        return value;
    }

    /**设置购物车中个数的方法*/
    public void setValue(int value) {
        this.value = value;

        //设置文本显示框中的个数（刷新UI）
        tvValue.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 当数量发生变化的时候回调
     */
    public interface OnNumberChangeListener{

        /**当数据发送变化的时候回调*/
        public void onNumberChange(int value);
    }

    /**接口 （有外界通过set实例化创建）数量变化的监听*/
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
