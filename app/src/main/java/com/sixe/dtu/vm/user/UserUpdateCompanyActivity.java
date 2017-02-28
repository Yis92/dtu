package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.http.entity.user.UserCompanyInfoResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserCompanyInfoListAdapter;
import com.sixe.dtu.vm.adapter.user.UserUpdateCompanyListAdapter;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 修改单位信息
 * Created by liu on 17/2/28.
 */

public class UserUpdateCompanyActivity extends BaseActivity {

    private TextView tvTitle;
    private RelativeLayout rlBack;
    private ImageView ivSet;
    private RelativeLayout rlSet;

    private ListView lvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_update_company);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        rlBack = findView(R.id.rl_back);
        tvTitle = findView(R.id.tv_title);
        ivSet = findView(R.id.iv_set);
        rlSet = findView(R.id.rl_set);
        lvContent = findView(R.id.lv_content);
    }

    @Override
    public void initData(Intent intent) {
//        tvTitle.setText("修改单位信息");
//
//        rlBack.setVisibility(View.VISIBLE);
//        rlSet.setVisibility(View.VISIBLE);
//        ivSet.setBackgroundResource(R.mipmap.save);
//
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            list.add("val");
//        }
//        UserUpdateCompanyListAdapter adapter = new UserUpdateCompanyListAdapter(activity, list);
//        lvContent.setAdapter(adapter);
//
//        String uint_id = intent.getExtras().getString("unit_id");
//
//        queryHttp(uint_id);
    }

    @Override
    public void initEvents() {
        //返回
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //修改单位信息
        rlSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void queryHttp(String unit_id) {

        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("unit_id", unit_id);

            HttpManager.postAsyn(HttpConstant.QUERY_COMPANY, new HttpManager.ResultCallback<UserCompanyInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(UserCompanyInfoResp response) {
                    if (response != null && response.getState() == 200) {

                    }
                }
            }, map);

        }
    }

    @Override
    public Class<?> getClazz() {
        return UserUpdateCompanyActivity.class;
    }
}
