package com.loveplusplus.update;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

class UpdateDialog {

    static void show(final Context context, String content, final String downloadUrl, View view, int isMustUpdate) {
//        if (isContextValid(context)) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle(R.string.android_auto_update_dialog_title);
//            builder.setMessage(Html.fromHtml(content))
//                    .setPositiveButton(R.string.android_auto_update_dialog_btn_download, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            goToDownload(context, downloadUrl);
//                        }
//                    })
//                    .setCancelable(false)
//                    .setNegativeButton(R.string.android_auto_update_dialog_btn_cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//
//            AlertDialog dialog = builder.create();
//            //点击对话框外面,对话框不消失
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//        }


        if (isMustUpdate == 1) {
            //用于判断是否是back键返回的
            final boolean[] isClick = new boolean[1];
            final CustomDialog.Builder customDialog = new CustomDialog.Builder(context, isMustUpdate);
            customDialog.setTitle(content)
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (isWifi(context)) {
                                goToDownload(context, downloadUrl);
                                customDialog.setUpdateClick(false, true);
                            } else {
                                //
                                SweetAlertDialog sd = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                                sd.setCanceledOnTouchOutside(true);
                                sd.setTitleText("确定使用流量更新?")
                                        .setCancelText("取消")
                                        .setConfirmText("确定")
                                        .showCancelButton(true)
                                        //取消按钮
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                isClick[0] = false;
                                                //解放确定按钮的点击时间
                                                customDialog.setUpdateClick(true, false);
                                                sDialog.dismiss();
                                            }
                                        })
                                        //确定按钮
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                isClick[0] = true;
                                                goToDownload(context, downloadUrl);
                                                customDialog.setUpdateClick(false, true);
                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                        .show();

                                sd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if (!isClick[0]) {
                                            customDialog.setUpdateClick(true, false);
                                        }
                                    }
                                });
                            }
                        }
                    })
                    .create().show();
        } else {
            //用于判断是否是back键返回的
            final boolean[] isClick = new boolean[1];

            final CustomDialog.Builder customDialog = new CustomDialog.Builder(context, isMustUpdate);
            customDialog.setTitle(content)
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            if (isWifi(context)) {
                                goToDownload(context, downloadUrl);
                                dialog.dismiss();
                            } else {
                                //
                                SweetAlertDialog sd = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                                sd.setCanceledOnTouchOutside(true);
                                sd.setTitleText("确定使用流量更新")
                                        .setCancelText("取消")
                                        .setConfirmText("确定")
                                        .showCancelButton(true)
                                        //取消按钮
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                isClick[0] = false;
                                                customDialog.setUpdateClick(true, false);
                                                sDialog.dismiss();
                                            }
                                        })
                                        //确定按钮
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                isClick[0] = true;
                                                goToDownload(context, downloadUrl);
                                                customDialog.setUpdateClick(false, true);
                                                sweetAlertDialog.dismiss();
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();

                                sd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if (!isClick[0]) {
                                            customDialog.setUpdateClick(true, false);
                                        }
                                    }
                                });
                            }
                        }
                    })
                    .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }

        //
        //设置contentView
//        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_update_hint, null);
//        final PopupWindow mPopWindow = new PopupWindow(contentView,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//        mPopWindow.setContentView(contentView);
//        mPopWindow.setFocusable(true);
//        //外部是否可以点击
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopWindow.setOutsideTouchable(true);
//        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
//        LinearLayout llContent = (LinearLayout) contentView.findViewById(R.id.ll_content);
//        Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
//        Button btnDownload = (Button) contentView.findViewById(R.id.btn_download);
//        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
//        llContent.getBackground().setAlpha(150);
//        tvContent.setText(Html.fromHtml(content));
//
//        // 下次再说
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPopWindow.dismiss();
//            }
//        });
//
//        // 立即下载
//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToDownload(context, downloadUrl);
//                mPopWindow.dismiss();
//            }
//        });
//
//        // 关闭pop
//        ivClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPopWindow.dismiss();
//            }
//        });
//
//        //显示PopupWindow
//        mPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }


    private static void goToDownload(Context context, String downloadUrl) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }

    /**
     * 判断是否为eifi
     *
     * @param mContext
     * @return
     */
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
