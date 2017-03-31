package com.sixe.dtu.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sixe.dtu.AppApplication;
import com.sixe.dtu.R;

import cn.trinea.android.common.base.CommonCompatActivity;
import cn.trinea.android.common.util.PreferenceHelper;

/**
 * 基础Activity
 * Created by liuyi on 15/10/.
 */
public abstract class BaseActivity extends CommonCompatActivity {

    private PreferenceHelper preferenceHelper;

    public RelativeLayout rlBack;
    public TextView tvTitle;
    public RelativeLayout rlSet;
    public ImageView ivSet;

    @Override
    protected void onCreate(Bundle savedInstanceState, int layoutResID) {
        super.onCreate(savedInstanceState, layoutResID);
//        getSupportActionBar().hide();
    }

    @Override
    public void preSetContentView() {
        super.preSetContentView();
        // 设置页面竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        preferenceHelper = AppApplication.getPreferenceHelper();
    }

    @Override
    public void preInit(Bundle savedInstanceState) {
        super.preInit(savedInstanceState);
        rlBack = findView(R.id.rl_back);
        tvTitle = findView(R.id.tv_title);
        rlSet = findView(R.id.rl_set);
        ivSet = findView(R.id.iv_set);
    }

    public boolean isEmpty(Object obj) {
        return obj == null;
    }

    public boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


    public PreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    public boolean noNetWork() {
        return !hasNetWork();
    }

    public void showNetWorkError() {
        showToast("请检查您的网络!");
    }


    /**
     * 获取手机唯一标识
     *
     * @return
     */
    public String getIMEI() {
        TelephonyManager TelephonyMgr = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    public void showToastResult(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.swiperefresh_color3);
        snackbar.show();
    }

}
