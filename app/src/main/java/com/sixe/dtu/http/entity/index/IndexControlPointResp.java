package com.sixe.dtu.http.entity.index;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 控制节点返回信息
 * Created by Administrator on 2017/3/31.
 */

public class IndexControlPointResp extends CommonResponse<List<IndexControlPointResp>> {

    private String name;//控制器名称
    private String cfg;//控制器类型码
    private String addr;//控制器地址
    private String describ;//控制器描述
    private String x;//控制器站内坐标x
    private String y;//控制器站内坐标y
    private String tsknum;//控制器任务数
    private List<TskdescribBean> tskdescrib;//通道描述

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
