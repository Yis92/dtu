package com.sixe.dtu.http.entity.index;

import com.sixe.dtu.http.util.CommonResponse;

/**
 * dtu信息 详情 -返回
 * Created by liu on 17/3/8.
 */

public class IndexDtuInfoResp extends CommonResponse<IndexDtuInfoResp>{

    private String dtu_name;//DTU设备名称
    private String dtu_describ;//设备描述
    private String dtu_address;//安装位置
    private String dtu_long;//安装经度
    private String dtu_lat;//安装纬度
    private String dtu_comm_type;//通信类型
    private String dtu_sim_no;//sim卡号
    private String dtu_warning_type;//报警类型
    private String dtu_upfreq;//上传频率


    public String getDtu_name() {
        return dtu_name;
    }

    public void setDtu_name(String dtu_name) {
        this.dtu_name = dtu_name;
    }

    public String getDtu_describ() {
        return dtu_describ;
    }

    public void setDtu_describ(String dtu_describ) {
        this.dtu_describ = dtu_describ;
    }

    public String getDtu_address() {
        return dtu_address;
    }

    public void setDtu_address(String dtu_address) {
        this.dtu_address = dtu_address;
    }

    public String getDtu_long() {
        return dtu_long;
    }

    public void setDtu_long(String dtu_long) {
        this.dtu_long = dtu_long;
    }

    public String getDtu_lat() {
        return dtu_lat;
    }

    public void setDtu_lat(String dtu_lat) {
        this.dtu_lat = dtu_lat;
    }

    public String getDtu_comm_type() {
        return dtu_comm_type;
    }

    public void setDtu_comm_type(String dtu_comm_type) {
        this.dtu_comm_type = dtu_comm_type;
    }

    public String getDtu_sim_no() {
        return dtu_sim_no;
    }

    public void setDtu_sim_no(String dtu_sim_no) {
        this.dtu_sim_no = dtu_sim_no;
    }

    public String getDtu_warning_type() {
        return dtu_warning_type;
    }

    public void setDtu_warning_type(String dtu_warning_type) {
        this.dtu_warning_type = dtu_warning_type;
    }

    public String getDtu_upfreq() {
        return dtu_upfreq;
    }

    public void setDtu_upfreq(String dtu_upfreq) {
        this.dtu_upfreq = dtu_upfreq;
    }
}
