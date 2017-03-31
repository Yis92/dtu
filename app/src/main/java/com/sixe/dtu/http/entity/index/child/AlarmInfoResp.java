package com.sixe.dtu.http.entity.index.child;

import com.sixe.dtu.http.util.CommonResponse;

import java.util.List;

/**
 * 报警信息返回
 * Created by Administrator on 2017/3/30.
 */

public class AlarmInfoResp extends CommonResponse<List<AlarmInfoResp>>   {
    /**
     * msgid : 1
     * msg : 办公室(节点1)温度 测量值 141 超上限 100 ,报警
     * dispose : 1
     */

    private String msgid;
    private String msg;
    private String tm;
    private String dispose;//是否处理过

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDispose() {
        return dispose;
    }

    public void setDispose(String dispose) {
        this.dispose = dispose;
    }


//    private String msgid;//要素名称
//    private String msg;//要素说明
//    private String dt;//这个可以不显示出来，但设置的时候需要回传
//    private String dispose;//报警上限
//
//    public String getMsgid() {
//        return msgid;
//    }
//
//    public void setMsgid(String msgid) {
//        this.msgid = msgid;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    @Override
//    public String getDt() {
//        return dt;
//    }
//
//    @Override
//    public void setDt(String dt) {
//        this.dt = dt;
//    }
//
//    public String getDispose() {
//        return dispose;
//    }
//
//    public void setDispose(String dispose) {
//        this.dispose = dispose;
//    }
}
