package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
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
 * 查看单位信息
 * Created by liu on 17/2/28.
 */

public class UserCompanyInfoActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private TextView tvTitle;
    private ImageView ivSet;
    private RelativeLayout rlSet;

    private ListView lvContent;
    private String uint_id;//公司编号

    UserUpdateCompanyListAdapter adapter;
    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_company_info);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
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

        //获取用户等级
        int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, -1);

        rlBack.setVisibility(View.VISIBLE);
        tvTitle.setText("单位信息");

        if (user_level == 10) {
            rlSet.setVisibility(View.VISIBLE);
            ivSet.setBackgroundResource(R.mipmap.update);
        }

        List<UserCompanyInfoResp.UserCompanyInfo> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserCompanyInfoResp.UserCompanyInfo companyInfo = new UserCompanyInfoResp().new UserCompanyInfo();
            companyInfo.setName("name");
            companyInfo.setValue("value" + i);
            dataList.add(companyInfo);
        }
         adapter = new UserUpdateCompanyListAdapter(activity, dataList);
        lvContent.setAdapter(adapter);

        uint_id = intent.getStringExtra("unit_id");



//        http(uint_id, user_level);
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
                List<UserCompanyInfoResp.UserCompanyInfo> temp = adapter.getUpdateData();
                for (int i = 0; i < temp.size(); i++) {
                    Log.i("http", "=======" + temp.get(i).getValue() + "=====");
                }
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void http(String unit_id, final int user_level) {

        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("unit_no", unit_id);

            httpLoadingDialog.visible();

            HttpManager.postAsyn(HttpConstant.QUERY_COMPANY, new HttpManager.ResultCallback<UserCompanyInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(UserCompanyInfoResp response) {
                    if (response != null && response.getState() == 200) {
                        if (user_level == 10) {
                            UserUpdateCompanyListAdapter adapter = new UserUpdateCompanyListAdapter(activity, response.getResult().getCompany());
                            lvContent.setAdapter(adapter);
                        } else {
                            UserCompanyInfoListAdapter adapter = new UserCompanyInfoListAdapter(activity, response.getResult().getCompany());
                            lvContent.setAdapter(adapter);
                        }
                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);

        }
    }

    @Override
    public Class<?> getClazz() {
        return UserCompanyInfoActivity.class;
    }
}
