package com.sixe.dtu.http.entity.user;

import android.widget.TextView;

import com.sixe.dtu.http.util.CommonResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liu on 17/3/1.
 */

public class UserStaffManagerResp extends CommonResponse<List<UserStaffManagerResp>> implements Serializable {

    private String user_id;//用户名
    private int user_level;//用户等级
    private String user_full_name;//全名
    private String user_describ;//用户描述
    private String user_tel1;//电话1
    private String user_tel2;//电话2


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

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUser_describ() {
        return user_describ;
    }

    public void setUser_describ(String user_describ) {
        this.user_describ = user_describ;
    }

    public String getUser_tel1() {
        return user_tel1;
    }

    public void setUser_tel1(String user_tel1) {
        this.user_tel1 = user_tel1;
    }

    public String getUser_tel2() {
        return user_tel2;
    }

    public void setUser_tel2(String user_tel2) {
        this.user_tel2 = user_tel2;
    }
}
