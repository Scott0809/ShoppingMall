package com.jingchen.tbviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Created by 求知自学网 on 2019/6/1 Copyright © Leon. All rights reserved.
 * Functions: Android仿淘宝商品浏览控件，用手机淘宝浏览商品详情时，商品图片是放在后面的，在第一个
 * ScrollView滚动到最底下时会有提示，继续拖动才能浏览下一个ScrollView里的图片。
 *
 * GitHub地址：https://github.com/jingchenUSTC/scrollViewContainer
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
