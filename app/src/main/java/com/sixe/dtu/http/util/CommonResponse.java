package com.sixe.dtu.http.util;

import java.io.Serializable;

/**
 * CommonResponse 接收返回的错误信息
 */
public  class CommonResponse<T> implements Serializable {

    private T result;


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
