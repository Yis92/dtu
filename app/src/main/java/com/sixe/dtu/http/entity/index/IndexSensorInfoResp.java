package com.sixe.dtu.http.entity.index;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 传感器节点信息
 * Created by Administrator on 2017/3/30.
 */

public class IndexSensorInfoResp extends CommonResponse<List<IndexSensorInfoResp>> {

    private String name;//节点名称
    private String cfg;//节点类型
    private String addr;//节点地址
    private String describ;//节点描述
    private String x;//x坐标
    private String y;//y坐标

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
}
