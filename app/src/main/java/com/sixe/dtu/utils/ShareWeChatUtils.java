package com.sixe.dtu.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.sixe.dtu.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.trinea.android.common.netstate.NetWorkUtil;
import cn.trinea.android.common.util.ToastUtils;
import okio.BufferedSink;
import okio.Okio;

/**
 * 通过intent分享到微信
 * Created by liuyi on 2016/6/12.
 */
public class ShareWeChatUtils {

    private Context context;
    private String[] urls;//分享的图片
    private String shareTitle;//分享的标题

    private int nid;

    public ShareWeChatUtils(Context context, String[] urls, String shareTitle, int nid) {
        this.context = context;
        this.urls = urls;
        this.shareTitle = shareTitle;
        this.nid = nid;
    }

    public ShareWeChatUtils(Context context, String[] urls, String shareTitle) {
        this.context = context;
        this.urls = urls;
        this.shareTitle = shareTitle;
    }

    /**
     * 分享到微信朋友圈
     */
    public void share() {
        ToastUtils.show(context, "分享中...");
        new MyTask(urls).execute();
    }

    private class MyTask extends AsyncTask<Void, Integer, Void> {

        private String[] mUrls;

        public MyTask(String[] urls) {
            this.mUrls = urls;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 0; i < mUrls.length; i++) {

                downloadPicToSDCard(mUrls[i]);

                publishProgress(i);

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //下载完成
            ArrayList<Uri> uris = new ArrayList<>();

            if (urls != null && urls.length > 0) {

                for (String url : urls) {
                    File file = getFileByUrl(url);

                    if (file.exists()) {
                        uris.add(Uri.fromFile(file));
                    }
                }

                if (uris.isEmpty()) {
//                Toast.makeText(context, "请先下载图片", Toast.LENGTH_SHORT).show();
                    //如果图片下载不下来  默认给了一张知晓logo的图片作为分享的图片
                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.dihui);
                    Uri uri2 = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, null, null));

                    ArrayList<Uri> uris2 = new ArrayList<>();
                    uris2.add(uri2);
                    shareToTimeLine(shareTitle, uris2);
                } else {
                    String title = shareTitle;
                    shareToTimeLine(title, uris);
                }
            } else {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.dihui);
                Uri uri2 = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, null, null));

                ArrayList<Uri> uris2 = new ArrayList<>();
                uris2.add(uri2);
                shareToTimeLine(shareTitle, uris2);
            }
        }
    }

    /**
     * 分享多图到朋友圈,多张图片加文字
     *
     * @param uris
     */
    private void shareToTimeLine(String title, ArrayList<Uri> uris) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");

        intent.putExtra("Kdescription", title);

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        context.startActivity(intent);
    }


    /**
     * 当图片下载失败的时候，只分享文字到朋友圈
     */
    private void shareToTimeLineNoImage(String title, String imgPath) {
//        Intent intent = new Intent();
//        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//        intent.setComponent(comp);
//        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
//        intent.setType("text/plain"); // 纯文本
//
//        intent.putExtra("Kdescription", title);
//
//        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
//        context.startActivity(intent);

        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
//        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, shareTitle);
        //加上这句代码会导致部分机型无法跳转回应用
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    /**
     * @param url
     * @return
     */
    private boolean downloadPicToSDCard(final String url) {

        final File file = getFileByUrl(url);

        if (file.exists()) {
//            Toast.makeText(MainActivity.this, "图片已经下载", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            final OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();


            try {
                Response response = client.newCall(request).execute();

                BufferedSink sink = Okio.buffer(Okio.sink(file));
                sink.writeAll(response.body().source());
                sink.close();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private File getFileByUrl(String url) {
        String filename = MD5Utils.md5(url);
        return new File(context.getExternalCacheDir(), filename);
    }

    /**
     * 分享到微信好友
     *
     * @param imgPath
     */
    public void shareWeChat(String imgPath) {
        ToastUtils.show(context, "分享中...");
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
//        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, shareTitle);
        //加上这句代码会导致部分机型无法跳转回应用
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
    /**
     * 分享本地图片到朋友圈
     */
    public void shareLocalImg(){
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.dihui_logo);
        Uri uri2 = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, null, null));

        ArrayList<Uri> uris2 = new ArrayList<>();
        uris2.add(uri2);
        shareToTimeLine(shareTitle, uris2);
    }

}
