package com.sixe.dtu.http.entity.user;

import com.sixe.dtu.http.util.CommonResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录 - 返回
 * Created by liu on 17/2/25.
 */

public class UserLoginResp extends CommonResponse<UserLoginResp> implements Serializable {

    private String user_id;//用户id
    private int user_level;//用户等级，其中，10：公司管理员，11：高级用户，12：普通用户
    private String unit_num;//关联公司个数，最大10个
    private List<Company> units;//关联的公司的数组

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
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

    public class Company implements Serializable {

        private String unit_no;//公司编号：注意，此编号唯一
        private String unit_name;//公司名
        private String dtu_num;//公司名下的dtu数量最大64个
        private ArrayList<DtuName> dtu;//dtu信息

        public ArrayList<DtuName> getDtu() {
            return dtu;
        }

        public void setDtu(ArrayList<DtuName> dtu) {
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

        public class DtuName implements Serializable {
            private String dtu_name;//dtu名字
            private String dtu_sn;//dtu编号

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
    }

}
