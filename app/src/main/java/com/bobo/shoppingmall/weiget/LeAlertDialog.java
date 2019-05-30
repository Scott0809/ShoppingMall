package com.bobo.shoppingmall.weiget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.utils.Constants;
import com.bobo.shoppingmall.utils.SpUtils;

/**
 * Created by Leon on 2018/5/19.
 * Functions: 自定义dialog
 */

public class LeAlertDialog extends Dialog{

    private Context context;
    private String title;
    private String dialogOkTitle;
    private String dialogCancelTitle;
    private boolean rotation;

    //要执行动画的imageview
    private ImageView le_image;

    private static final int ANIMA = 1;

    //使用这个dialog的类必须实现这个接口
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    /***
     * @param context 上下文
     * @param title 标题例如：您胜利了
     * @param  themeResId  传风格（style） 注意不要传图片
     * @param dialogOkTitle 左边按钮的标题  传 null 不显示本按钮
     * @param dialogCancelTitle 右边按钮的标题 传 null 不显示本按钮
     * @param rotation 旋转的方向true为正传 false为反转
     */
    public LeAlertDialog(@NonNull Context context, int themeResId, String title,
                         String dialogOkTitle, String dialogCancelTitle,boolean rotation) {
        super(context, themeResId);
        this.context = context;
        this.title = title;
        this.dialogOkTitle = dialogOkTitle;
        this.dialogCancelTitle = dialogCancelTitle;
        this.rotation = rotation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    //动画的处理
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case ANIMA:
                    Animation tipAnim1;//正/倒转动画
                    Animation tipAnim2;//正/倒转动画
                    if (!rotation){
                        tipAnim1 = AnimationUtils.loadAnimation(context,R.anim.small_tip_animation);
                        le_image.startAnimation(tipAnim1);
                    }else {
                        tipAnim2 = AnimationUtils.loadAnimation(context,R.anim.small_back_animation);
                        le_image.startAnimation(tipAnim2);
                    }
                    sendEmptyMessageDelayed(ANIMA,3000);
                    break;

            }
        }
    };


    private void init() {
        mHandler.sendEmptyMessage(ANIMA);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.le_alertdialog, null);
        setContentView(view);


        TextView dialogTitle = (TextView)findViewById( R.id.dialog_title );

        Button dialogOk = (Button)findViewById( R.id.dialog_ok );
        Button dialogCancel = (Button)findViewById( R.id.dialog_cancel );

        //設置自定義字體 字體包在assets/font/test.ttf
        dialogOk.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/test.ttf"));
        dialogCancel.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/test.ttf"));

        le_image = (ImageView)findViewById(R.id.le_image);

        dialogTitle.setText(title);

        if (dialogOkTitle != null){
            dialogOk.setVisibility(View.VISIBLE);
            dialogOk.setText(dialogOkTitle);
            dialogOk.setOnClickListener(new clickListener());
        }else {
            dialogOk.setVisibility(View.GONE);
        }

        if (dialogCancelTitle != null){
            dialogCancel.setVisibility(View.VISIBLE);
            dialogCancel.setText(dialogCancelTitle);
            dialogCancel.setOnClickListener(new clickListener());
        }else {
            dialogCancel.setVisibility(View.GONE);
        }


//網上找的代碼是這樣寫的我看沒有什麽用就注釋了
//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }


    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.dialog_ok:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.dialog_cancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    };

    @Override
    protected void onStop() {

        if (mHandler != null){
            mHandler.removeMessages(ANIMA);
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        super.onStop();
    }

    @Override
    public void onBackPressed() {

        //如果时强制更新什么都不做
        if (SpUtils.getBoolean(context, Constants.MANDATORY_UPDATES)){
            return;
        }

        super.onBackPressed();
    }

    /**使用方法
     *
     * final ConfirmDialog confirmDialog = new ConfirmDialog(context, "确定要退出吗?", "退出", "取消");
     3         confirmDialog.show();
     4         confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
     5             @Override
     6             public void doConfirm() {
     7                 // TODO Auto-generated method stub
     8                 confirmDialog.dismiss();
     9                 //toUserHome(context);
     10                 AppManager.getAppManager().AppExit(context);
     11             }
     12
     13             @Override
     14             public void doCancel() {
     15                 // TODO Auto-generated method stub
     16                 confirmDialog.dismiss();
     17             }
     18         });
     19     }
     *
     **/


}