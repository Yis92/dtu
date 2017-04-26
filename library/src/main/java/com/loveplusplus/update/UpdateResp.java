package com.loveplusplus.update;

/**
 * Created by liu on 16/8/22.
 */
public class UpdateResp {

    private String message;
    private String appUrl;
    private int verCode;
//        private int force;//是否强制更新,1:强制更新


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }


}
