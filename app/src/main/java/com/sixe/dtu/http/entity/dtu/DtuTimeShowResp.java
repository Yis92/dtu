package com.sixe.dtu.http.entity.dtu;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * dtu实时数据
 * Created by liu on 17/3/2.
 */

public class DtuTimeShowResp {

    private int state;
    private String message;
    private String dt;
    private List<List<String>> result;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<List<String>> getResult() {
        return result;
    }

    public void setResult(List<List<String>> result) {
        this.result = result;
    }
}
