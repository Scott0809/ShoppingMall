package com.bobo.shoppingmall.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.bobo.shoppingmall.weiget.LeAlertDialog;
import com.bobo.shoppingmall.R;
import com.maning.updatelibrary.InstallUtils;

/**
 * Created by 求知自学网 on 2019/5/29. Copyright © Leon. All rights reserved.
 * Functions: 版本更新的类 使用了极光推送 自定义消息给所有用户
 */
public class UpdateUtils {

    public static void checkforUpdate(Context context,Activity activity){

        //是否强制更新
        boolean mandatoryUpdates = SpUtils.getBoolean(context, Constants.MANDATORY_UPDATES);
        //是否普通更新
        boolean update = SpUtils.getBoolean(context, Constants.UPDATES);

        //优先检查是否强制更新
        if (mandatoryUpdates){
            updateMethod(context,Constants.MANDATORY_UPDATES,activity);
        }else if (update){//再检查是否更新
            updateMethod(context,Constants.UPDATES,activity);
        }
    }

    public static void updateMethod(final Context context, final String udateType, final Activity activity){
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(context);
        pd.setCancelable(false);// 必须一直下载完，不可取消
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后");
        pd.setTitle("版本升级");


        final LeAlertDialog leAlertDialog;

        if (udateType.equals(Constants.MANDATORY_UPDATES)){
            //处理强制更新
            leAlertDialog = new LeAlertDialog(context, R.style.dialog, "重要更新",
                    "更新", null, true);
        }else{
            //处理普通更新
            leAlertDialog = new LeAlertDialog(context, R.style.dialog,"新版本更新",
                    "更新", "取消", false);
        }

        //开始显示（询问用户更新意见）
        leAlertDialog.show();
        //点击其他空白区域Dialog不会消失
        leAlertDialog.setCanceledOnTouchOutside(false);


        //处理用户点击事件
        leAlertDialog.setClicklistener(new LeAlertDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                //用户点击了更新
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //先获取是否有安装未知来源应用的权限
                    boolean haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                    if (!haveInstallPermission) {
                        //跳转设置开启允许安装
                        Uri packageURI = Uri.parse("package:"+context.getPackageName());
                        Intent intent =new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
                        activity.startActivityForResult(intent,1000);
                        return;
                    }
                }
                InstallUtils.with(context).setApkUrl("url").setApkName("波波商城")
                        .setCallBack(new InstallUtils.DownloadCallBack() {
                            @Override
                            public void onStart() {
                                pd.show();
                            }

                            @Override
                            public void onComplete(String s) {
                                pd.dismiss();

                                InstallUtils.installAPK(context, s, new InstallUtils.InstallCallBack() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFail(Exception e) {
                                    }
                                });
                            }

                            @Override
                            public void onLoading(long total, long current) {
                                pd.setMax((int) total);
                                pd.setProgress((int) current);
                                LELog.showLogWithLineNum(5,"total = "+total+"current = " +
                                        ""+current);
                            }

                            @Override
                            public void onFail(Exception e) {
                                //更新成功了持久化保存的字段设为false
                                SpUtils.setBoolean(context,udateType,false);
                                LELog.showLogWithLineNum(5,"更新成功"+Thread.currentThread().getName());
                                //Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }

                            @Override
                            public void cancle() {
                                //Toast.makeText(context,"更新失败",Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }).startDownload();

            }

            @Override
            public void doCancel() {
                //用户点击了取消 解散dialog什么都不做
                leAlertDialog.dismiss();
            }
        });


//        AlertDialog alertDialog = AlertDialog.newInstance(getString(R.string.has_new_version));
//        alertDialog.setOnDialogClickListener(new AlertDialog.OnDialogClickListener() {
//            @Override
//            public void onPositive() {
//
//        });
//        alertDialog.show(this);
    }

}
