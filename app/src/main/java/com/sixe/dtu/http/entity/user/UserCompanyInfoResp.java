package com.sixe.dtu.http.entity.user;

import com.sixe.dtu.http.util.CommonResponse;

/**
 * 单位信息- 返回
 * Created by liu on 17/2/28.
 */

public class UserCompanyInfoResp extends CommonResponse<UserCompanyInfoResp>{

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
