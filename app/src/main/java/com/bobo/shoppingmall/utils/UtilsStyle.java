package com.bobo.shoppingmall.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Leon on 2019/1/12 Copyright © Leon. All rights reserved.
 * Functions:  设置状态栏透明并改变状态栏颜色为深色工具类
 */
public class UtilsStyle {


    private static boolean mIsDark = false;


    /**
     * 设置魅族手机状态栏图标颜色风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置小米手机状态栏字体图标颜色模式，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {//状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {//清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }



    /**
     * 在不知道手机系统的情况下尝试设置状态栏字体模式为深色
     * 也可以根据此方法判断手机系统类型
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0 0:设置失败
     */
    public static void statusBarLightMode(Activity activity,boolean isDark) {


         mIsDark = isDark;


        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mIsDark == true) {

            //设置状态栏黑色字体

            if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                //result = 1;
                if (Build.VERSION.SDK_INT >= 21){
                    //注意高版本的小米手机只能用通用方法处理
                    StatusBarLightMode(activity, 3);
                }else {
                    StatusBarLightMode(activity, 1);
                }
                //StatusBarLightMode(activity, 1);
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                //result = 2;
                StatusBarLightMode(activity, 2);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //result = 3;
                StatusBarLightMode(activity, 3);
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mIsDark == false){
            //设置状态栏白色字体

            if (MIUISetStatusBarLightMode(activity.getWindow(), false)) {
                //result = 1;
                StatusBarLightMode2(activity, 1);
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), false)) {
                //result = 2;
                StatusBarLightMode2(activity, 2);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //result = 3;
                StatusBarLightMode2(activity, 3);
            }
        }
    }

    /**
     * 已知系统类型时，设置状态栏字体图标为深色。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    /**
     * 已知系统类型时，设置状态栏字体图标为深色。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode2(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    /**
     * 已知系统类型时，清除MIUI或flyme或6.0以上版本状态栏字体深色模式 这个方法没有用到过
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }


}
