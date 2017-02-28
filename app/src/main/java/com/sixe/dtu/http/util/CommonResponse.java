package com.sixe.dtu.http.util;

/**
 * CommonResponse 接收返回的错误信息
 */
public  class CommonResponse<T> {

    private T result;


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
