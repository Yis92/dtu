package com.sixe.dtu.http.entity.index.child;

import com.sixe.dtu.http.util.CommonResponse;

/**
 * 查询报警信息
 * Created by Administrator on 2017/4/25.
 */

public class QueryAlarmInfoResp extends CommonResponse<QueryAlarmInfoResp> {


    /**
     * name : (02) 温度
     * describ : 上下限值扩大10倍
     * data_no : 1
     * up : 1000
     * low : 100
     * lasting : 100
     * interval : 10
     * enable : 1
     */

    private String name;
    private String describ;
    private int data_no;
    private String up;
    private String low;
    private String lasting;
    private String interval;
    private String enable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public int getData_no() {
        return data_no;
    }

    public void setData_no(int data_no) {
        this.data_no = data_no;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLasting() {
        return lasting;
    }

    public void setLasting(String lasting) {
        this.lasting = lasting;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
