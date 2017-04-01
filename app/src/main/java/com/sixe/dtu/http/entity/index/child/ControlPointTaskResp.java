package com.sixe.dtu.http.entity.index.child;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 控制节点任务状态 - 返回
 * Created by Administrator on 2017/4/1.
 */

public class ControlPointTaskResp extends CommonResponse<List<ControlPointTaskResp>> {

    private String tsk_channel;//任务通道号 1-8
    private String tsk_describ;//通道描述
    private String tsk_type;//任务类型 0：立即关闭，1：立即打开，2：计划打开，3：没有任务
    private String tsk_dt;//任务执行日期
    private String tsk_tm;//任务执行时间
    private String tsk_second;//任务执行周期 秒

    public String getTsk_channel() {
        return tsk_channel;
    }

    public void setTsk_channel(String tsk_channel) {
        this.tsk_channel = tsk_channel;
    }

    public String getTsk_describ() {
        return tsk_describ;
    }

    public void setTsk_describ(String tsk_describ) {
        this.tsk_describ = tsk_describ;
    }

    public String getTsk_type() {
        return tsk_type;
    }

    public void setTsk_type(String tsk_type) {
        this.tsk_type = tsk_type;
    }

    public String getTsk_dt() {
        return tsk_dt;
    }

    public void setTsk_dt(String tsk_dt) {
        this.tsk_dt = tsk_dt;
    }

    public String getTsk_tm() {
        return tsk_tm;
    }

    public void setTsk_tm(String tsk_tm) {
        this.tsk_tm = tsk_tm;
    }

    public String getTsk_second() {
        return tsk_second;
    }

    public void setTsk_second(String tsk_second) {
        this.tsk_second = tsk_second;
    }
}
