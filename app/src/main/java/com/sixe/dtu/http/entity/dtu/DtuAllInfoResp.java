package com.sixe.dtu.http.entity.dtu;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 改公司所有dtu信息
 * Created by liu on 17/3/2.
 */

public class DtuAllInfoResp extends CommonResponse<List<DtuAllInfoResp>> {

    private String dtu_name;
    private String dtu_sn;

    public String getDtu_name() {
        return dtu_name;
    }

    public void setDtu_name(String dtu_name) {
        this.dtu_name = dtu_name;
    }

    public String getDtu_sn() {
        return dtu_sn;
    }

    public void setDtu_sn(String dtu_sn) {
        this.dtu_sn = dtu_sn;
    }
}
