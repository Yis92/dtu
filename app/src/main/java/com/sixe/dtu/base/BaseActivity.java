package com.sixe.dtu.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;


import com.sixe.dtu.AppApplication;

import cn.trinea.android.common.base.CommonCompatActivity;
import cn.trinea.android.common.util.PreferenceHelper;

/**
 * 基础Activity
 * Created by liuyi on 15/10/.
 */
public abstract class BaseActivity extends CommonCompatActivity {

    private PreferenceHelper preferenceHelper;

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

}
