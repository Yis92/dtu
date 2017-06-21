package com.sixe.dtu.vm.boot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.utils.ACache;
import com.sixe.dtu.vm.user.UserLoginActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 启动页
 * Created by sunny on 2016/3/22.
 */
public class StartActivity extends BaseActivity {

    private ImageView ivStart;//启动图

    private ImageLoader imageLoader = ImageLoader.getInstance();

    private Handler handler = new Handler();

    private ACache aCache;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_start);
    }

    @Override
    public void initBoot() {
        aCache = ACache.get(activity);
    }

    @Override
    public void initViews() {
        ivStart = findView(R.id.iv_start);
    }

    @Override
    public void initData(Intent intent) {
        bitmap = aCache.getAsBitmap("startImage");
        if (bitmap != null) {
            ivStart.setImageBitmap(bitmap);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(UserLoginActivity.class);
                    finish();
                }
            }, 2000);

            loadNetImg();
        } else {
            loadNetImg();
        }
    }

    @Override
    public void initEvents() {
    }

    /**
     * 加载网络图片
     */
    public void loadNetImg() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();

            String user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

            if (isEmpty(user_id)) {
                map.put("user_id", "0");
            } else {
                map.put("user_id", user_id);
            }

            HttpManager.postAsyn(HttpConstant.QUERRY_LOGIN_BG_PNG, new HttpManager.ResultCallback<CommonResponse<String>>() {
                @Override
                public void onError(Request request, Exception e) {
                    startActivity(UserLoginActivity.class);
                    finish();
                }

                @Override
                public void onResponse(final CommonResponse<String> response) {
                    String startURL = getPreferenceHelper().getString(Constant.XINWEN_START_URL, "");
                    //本地有图并且与后台传来的图片一致，则使用本地图片
                    if (bitmap != null && startURL.equals(response.getResult())) {
                        //前面已经处理过
//                                    ivStart.setImageBitmap(bitmap);
                    } else {
                        imageLoader.displayImage(response.getResult(), ivStart);
                        //将获取到的图片存到本地
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                bitmap = imageLoader.loadImageSync(response.getResult());
                                aCache.put("startImage", bitmap);
                            }
                        }).start();

                        //将图片地址存到本地用于判别与服务器传来的启动图片地址是否相同
                        getPreferenceHelper().putString(Constant.XINWEN_START_URL, "");

                        //开启APP
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(UserLoginActivity.class);
                                finish();
                            }
                        }, 2000);

                    }
                }
            }, map);
        } else {
            if (bitmap != null) {
                ivStart.setImageBitmap(bitmap);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(UserLoginActivity.class);
                        finish();
                    }
                }, 2000);

            } else {
                startActivity(UserLoginActivity.class);
                finish();
            }
        }
    }

    @Override
    public Class<?> getClazz() {
        return StartActivity.class;
    }
}
