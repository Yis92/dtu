package com.sixe.dtu.vm.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.vm.test.UserResp;
import com.sixe.dtu.vm.test.UserTestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 17/2/26.
 */

public class UserTestActivity extends BaseActivity {

    private ListView listView;
    private ImageLoader imageLoader = ImageLoader.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_test);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        listView = findView(R.id.ll_content);
    }

    @Override
    public void initData(Intent intent) {
        //伪造数据
        List<UserResp> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserResp userResp = new UserResp();
            userResp.setText("不挂啊开始大口大口说的dasd啊");
            List<String> img = new ArrayList<>();
            img.add("http://img02.tooopen.com/images/20140504/sy_60294738471.jpg");
            img.add("http://img02.tooopen.com/images/20140504/sy_60294738471.jpg");
            img.add("http://img02.tooopen.com/images/20140504/sy_60294738471.jpg");
            userResp.setImages(img);
            list.add(userResp);
        }

        View llContent = View.inflate(activity, R.layout.layout_news, null);
        LinearLayout llNews = (LinearLayout) llContent.findViewById(R.id.ll_news);

        for (int i = 0; i < list.size(); i++) {
            UserResp resp = list.get(i);

            TextView textView = new TextView(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setText(resp.getText());

            llNews.addView(textView);

            for (int j = 0; j < resp.getImages().size(); j++) {
                final ImageView imageView = new ImageView(activity);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params2);
//                imageView.setImageBitmap(imageLoader.loadImageSync(resp.getImages().get(j)));
//                imageLoader.displayImage(resp.getImages().get(j), imageView);

                Glide.with(activity).load(resp.getImages().get(j)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        WindowManager wm = (WindowManager) activity
                                .getSystemService(Context.WINDOW_SERVICE);

                        int width = wm.getDefaultDisplay().getWidth();

                        int height = width * resource.getHeight() / resource.getWidth();
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        imageView.setLayoutParams(para);

                        imageView.setImageBitmap(resource);
                    }
                });

                llNews.addView(imageView);
            }
        }

        listView.addHeaderView(llContent);

        List<String> textData = new ArrayList<>();
        textData.add("qweee");
        textData.add("qweee");
        textData.add("qweee");
        textData.add("qweee");

        UserTestAdapter adapter = new UserTestAdapter(activity, textData);
        listView.setAdapter(adapter);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public Class<?> getClazz() {
        return UserTestActivity.class;
    }
}
