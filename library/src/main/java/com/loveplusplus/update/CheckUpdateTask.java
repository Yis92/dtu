package com.loveplusplus.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 19:21
 */
class CheckUpdateTask extends AsyncTask<Void, Void, String> {

    //    private ProgressDialog dialog;
    private Context mContext;
    private int mType;
    private boolean mShowProgressDialog;
    private static final String url = Constants.UPDATE_URL;
    private View view;

    CheckUpdateTask(Context context, int type, boolean showProgressDialog, View view) {

        this.mContext = context;
        this.mType = type;
        this.mShowProgressDialog = showProgressDialog;
        this.view = view;

    }


    protected void onPreExecute() {
//        if (mShowProgressDialog) {
//            dialog = new ProgressDialog(mContext);
//            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
//            dialog.show();
//        }
    }


    @Override
    protected void onPostExecute(String result) {

//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result, view);
        }
    }

    private void parseJson(String result, View view) {

//        Log.i("qqq","1231313");

//            JSONObject obj = new JSONObject(result);
//            String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
//            String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
//            int apkCode = obj.getInt(Constants.APK_VERSION_CODE);
        Gson gson = new Gson();
        UpdateResp updateResp = gson.fromJson(result, UpdateResp.class);
        Log.i("qqq", result);
        String updateMessage = updateResp.getVerMessage();
        String apkUrl = updateResp.getAppUrl();
        int apkCode = updateResp.getVerCode();
//        int force = updateResp.getData().getForce();
        int force = 0;

        int versionCode = AppUtils.getVersionCode(mContext);

        Log.i("http","versionCode==============="+versionCode);
        Log.i("http","apkCode==============="+apkCode);

        if (apkCode > versionCode) {
            if (mType == Constants.TYPE_NOTIFICATION) {
                showNotification(mContext, updateMessage, apkUrl);
            } else if (mType == Constants.TYPE_DIALOG) {
                showDialog(mContext, updateMessage, apkUrl, view, force);
            }
        } else if (mShowProgressDialog) {
//            Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Show dialog
     */
    private void showDialog(Context context, String content, String apkUrl, View view, int isMustUpdate) {
        UpdateDialog.show(context, content, apkUrl, view, isMustUpdate);
    }

    /**
     * Show Notification
     */
    private void showNotification(Context context, String content, String apkUrl) {
        Intent myIntent = new Intent(context, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = context.getApplicationInfo().icon;
        Notification notify = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.android_auto_update_notify_ticker))
                .setContentTitle(context.getString(R.string.android_auto_update_notify_content))
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        notify.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notify);
    }

    @Override
    protected String doInBackground(Void... args) {
        Gson gson = new Gson();
        try {
            int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            User user = new User(versionCode);
            String par = gson.toJson(user);
            Log.i("qqq","更新接口请求参数::::"+par);
            Log.i("qqq","更新接口请求地址::::"+url);
            return HttpUtils.postDownloadJson(url, par);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public class User {
        private int code;

        public User(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
