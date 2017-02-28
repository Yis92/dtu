package com.sixe.dtu.http.entity.user;

import com.sixe.dtu.http.util.CommonResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录返回
 * Created by liu on 17/2/25.
 */

public class UserLoginResp extends CommonResponse<UserLoginResp> implements Serializable {

    private int state;//返回登录状态 0：成功 -1：失败--用户名或者密码错误
    private String user_id;//用户id
    private String user_level;//用户等级，其中，10：公司管理员，11：高级用户，12：普通用户
    private String unit_num;//关联公司个数，最大10个
    private List<Company> units;//关联的公司的数组

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUnit_num() {
        return unit_num;
    }

    public void setUnit_num(String unit_num) {
        this.unit_num = unit_num;
    }

    public List<Company> getUnits() {
        return units;
    }

    public void setUnits(List<Company> units) {
        this.units = units;
    }

    public class Company {

        private String unit_no;//公司编号：注意，此编号唯一
        private String unit_name;//公司名
        private String dtu_num;//公司名下的dtu数量最大64个
        private List<DtuName> dtu;//dtu信息

        public List<DtuName> getDtu() {
            return dtu;
        }

        public void setDtu(List<DtuName> dtu) {
            this.dtu = dtu;
        }

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

        public String getDtu_num() {
            return dtu_num;
        }

        public void setDtu_num(String dtu_num) {
            this.dtu_num = dtu_num;
        }

        class DtuName {
            private String dtu1_name;//dtu名字
            private String dtu1_sn;//dtu编号

            public String getDtu1_name() {
                return dtu1_name;
            }

            public void setDtu1_name(String dtu1_name) {
                this.dtu1_name = dtu1_name;
            }

            public String getDtu1_sn() {
                return dtu1_sn;
            }

            public void setDtu1_sn(String dtu1_sn) {
                this.dtu1_sn = dtu1_sn;
            }
        }
    }

}
