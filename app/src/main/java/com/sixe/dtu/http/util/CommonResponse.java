package com.sixe.dtu.http.util;

import java.io.Serializable;

/**
 * CommonResponse 接收返回的错误信息
 */
public class CommonResponse<T> implements Serializable {

    private int state;//200为成功
    private String message;
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
