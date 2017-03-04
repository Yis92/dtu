package com.sixe.dtu.http.entity.user;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 添加员工时 查询单位信息 - 返回
 * Created by liu on 17/3/2.
 */

public class UserAddUserQueryCompanyResp extends CommonResponse<List<UserAddUserQueryCompanyResp>> {

    private String unit_no;//单位编号
    private String unit_name;//单位名称


    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
