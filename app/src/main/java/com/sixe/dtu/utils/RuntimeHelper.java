package com.sixe.dtu.utils;

/**
 * 单例存储唯一变量
 * Created by sunny on 2016/4/24.
 */
public class RuntimeHelper {

    private static RuntimeHelper mInstance;
    private boolean isLogin;
    private boolean isRegister;

    private String url;//接口域名
    private String appkey;//微信appkey
    private String shareUrl;//分享链接

    private boolean isShowRedTask;

    private boolean isActivity;//app切换到桌面标识

    private int statisticsID;//统计活跃度所用id

    private float x;
    private float y;

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getStatisticsID() {
        return statisticsID;
    }

    public void setStatisticsID(int statisticsID) {
        this.statisticsID = statisticsID;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setIsActivity(boolean isActivity) {
        this.isActivity = isActivity;
    }

    public static synchronized RuntimeHelper getInstance() {
        if (mInstance == null) {
            mInstance = new RuntimeHelper();
        }
        return mInstance;
    }

    public static RuntimeHelper getmInstance() {
        return mInstance;
    }

    public static void setmInstance(RuntimeHelper mInstance) {
        RuntimeHelper.mInstance = mInstance;
    }

    public boolean isShowRedTask() {
        return isShowRedTask;
    }

    public void setIsShowRedTask(boolean isShowRedTask) {
        this.isShowRedTask = isShowRedTask;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
