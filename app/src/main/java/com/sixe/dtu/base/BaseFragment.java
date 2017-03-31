package com.sixe.dtu.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.view.View;

import com.sixe.dtu.AppApplication;
import com.sixe.dtu.R;

import cn.trinea.android.common.base.CommonFragment;
import cn.trinea.android.common.util.PreferenceHelper;

/**
 * 基础Fragment
 * Created by liuyi on 15/10/15.
 */
public abstract class BaseFragment extends CommonFragment {

    private PreferenceHelper preferenceHelper;

    @Override
    public void preInit(Bundle savedInstanceState) {
        super.preInit(savedInstanceState);
        preferenceHelper = AppApplication.getPreferenceHelper();
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

    public void showRefreshResult() {
        Snackbar snackbar = Snackbar.make(view, "已经是最新数据了哦~~~", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.swiperefresh_color3);
        snackbar.show();
    }

    public void showToastResult(String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.swiperefresh_color3);
        snackbar.show();
    }
}
