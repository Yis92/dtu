package com.loveplusplus.update;

/**
 * Created by liu on 16/8/22.
 */
public class UpdateResp {

    private int status;
    private String message;
    private UpdateRespData data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UpdateRespData getData() {
        return data;
    }

    public void setData(UpdateRespData data) {
        this.data = data;
    }

    class UpdateRespData{

        private String appDesc;
        private String appUrl;
        private int verCode;
        private int force;//是否强制更新,1:强制更新

        public int getForce() {
            return force;
        }

        public void setForce(int force) {
            this.force = force;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public String getAppDesc() {
            return appDesc;
        }

        public void setAppDesc(String appDesc) {
            this.appDesc = appDesc;
        }

        public int getVerCode() {
            return verCode;
        }

        public void setVerCode(int verCode) {
            this.verCode = verCode;
        }
    }

}
