package com.sixe.dtu.http.entity.user;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 单位信息- 返回
 * Created by liu on 17/2/28.
 */

public class UserCompanyInfoResp extends CommonResponse<UserCompanyInfoResp> {

    private List<UserCompanyInfo> company;


    public List<UserCompanyInfo> getCompany() {
        return company;
    }

    public void setCompany(List<UserCompanyInfo> company) {
        this.company = company;
    }

    public class UserCompanyInfo {
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

}
