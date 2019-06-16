package com.bobo.shoppingmall.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;


public class MessageCenterActivity extends Activity {
    private ImageButton ib_login_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaage_center);

        //美化状态栏
        statusBarSystemSet();

        ib_login_back = (ImageButton) findViewById(R.id.ib_login_back);

        ib_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //对不同的安卓手机状态栏透明 字体颜色为黑色处理
    private void statusBarSystemSet() {

        //动态计算statusBar高度
        StBarUtil.setOccupationHeight(this, null);

        //设置状态栏上的字体为黑色（OV系手机的状态栏字体是白色）-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(this, true);
    }

}
