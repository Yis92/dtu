package com.sixe.dtu.service;

/**
 * Created by Administrator on 2017/6/15.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.sixe.dtu.AppApplication;
import com.sixe.dtu.MainActivity;
import com.sixe.dtu.R;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {

    public DemoIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {

    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        //新建一个Notification管理器;
        //API level 11
        Notification.Builder builder = new Notification.Builder(this);//新建Notification.Builder对象
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        //PendingIntent点击通知后所跳转的页面
        String s = new String(msg.getPayload());
        Gson gson = new Gson();
        PushResp pushResp = gson.fromJson(s, PushResp.class);
        builder.setContentTitle(pushResp.getTitle());
        builder.setContentText(pushResp.getContent());
        builder.setSmallIcon(R.mipmap.dtu_icon);
        builder.setContentIntent(intent);//执行intent
        Notification notification = builder.getNotification();//将builder对象转换为普通的notification
        notification.flags |= Notification.FLAG_AUTO_CANCEL;//点击通知后通知消失
        manager.notify(1, notification);//运行notification
    }

    public static String byte2String(byte[] buff) {
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < buff.length; i++) {
            int tmp = buff[i] & 0XFF;
            String str = Integer.toHexString(tmp);
            if (str.length() == 1) {
                sbuf.append("0" + str);
            } else {
                sbuf.append(str);
            }
        }
        return sbuf.toString();
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        sendMessage(clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }


    /**
     *
     */
    public void sendMessage(String getui_id) {

        String user_id = AppApplication.getPreferenceHelper().getString(Constant.USER_ID, "");

        if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(getui_id)) {

            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", user_id);
            map.put("getui_id", getui_id);


            HttpManager.postAsyn(HttpConstant.UPDATE_USER_GETUI_ID, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(CommonResponse response) {

                }
            }, map);
        }
    }

}
