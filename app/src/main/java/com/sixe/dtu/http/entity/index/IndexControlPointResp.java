package com.sixe.dtu.http.entity.index;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 控制节点返回信息
 * Created by Administrator on 2017/3/31.
 */

public class IndexControlPointResp extends CommonResponse<List<IndexControlPointResp>> {


    /**
     * name : 无线 8通道控制
     * cfg : 209
     * addr : 3
     * describ : 8通道控制
     * x : 1
     * y : 1
     * tsknum : 2
     * tskdescrib : [{"tsk_describ":"通风"},{"tsk_describ":"灌溉"}]
     */

    private String name;
    private String cfg;
    private String addr;
    private String describ;
    private String x;
    private String y;
    private String tsknum;
    private List<TskdescribBean> tskdescrib;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCfg() {
        return cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getTsknum() {
        return tsknum;
    }

    public void setTsknum(String tsknum) {
        this.tsknum = tsknum;
    }

    public List<TskdescribBean> getTskdescrib() {
        return tskdescrib;
    }

    public void setTskdescrib(List<TskdescribBean> tskdescrib) {
        this.tskdescrib = tskdescrib;
    }

    public static class TskdescribBean {
        /**
         * tsk_describ : 通风
         */

        private String tsk_describ;

        public String getTsk_describ() {
            return tsk_describ;
        }

        public void setTsk_describ(String tsk_describ) {
            this.tsk_describ = tsk_describ;
        }
    }
}
