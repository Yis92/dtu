package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.vm.adapter.user.UserCompanyInfoListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 单位信息
 * Created by liu on 17/2/28.
 */

public class UserCompanyInfoActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private TextView tvTitle;

    private ListView lvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_company_info);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        rlBack = findView(R.id.rl_back);
        tvTitle = findView(R.id.tv_title);
        lvContent = findView(R.id.lv_content);
    }

    @Override
    public void initData(Intent intent) {
        rlBack.setVisibility(View.VISIBLE);
        tvTitle.setText("单位信息");

        List<String> list = new ArrayList<>();
        for (int i =0;i<30;i++) {
            list.add("val");
        }
        UserCompanyInfoListAdapter adapter = new UserCompanyInfoListAdapter(activity, list);
        lvContent.setAdapter(adapter);
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
    }

    @Override
    public Class<?> getClazz() {
        return UserCompanyInfoActivity.class;
    }
}
