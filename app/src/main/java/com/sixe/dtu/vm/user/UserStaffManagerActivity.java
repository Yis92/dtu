package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserStaffManagerResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserStaffManagerListAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 单位用户管理
 * Created by liu on 17/3/1.
 */

public class UserStaffManagerActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private TextView tvTitle;
    private RelativeLayout rlSet;
    private ImageView ivSet;

    private ListView lvContent;

    private String unit_no;//单位编号

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_staff_manager);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        rlBack = findView(R.id.rl_back);
        tvTitle = findView(R.id.tv_title);
        rlSet = findView(R.id.rl_set);
        ivSet = findView(R.id.iv_set);
        lvContent = findView(R.id.lv_content);
    }

    @Override
    public void initData(Intent intent) {

        tvTitle.setText("员工管理");
        rlBack.setVisibility(View.VISIBLE);
        rlSet.setVisibility(View.VISIBLE);

        ivSet.setBackgroundResource(R.mipmap.add_user);

        unit_no = intent.getStringExtra(Constant.UNIT_NO);

        queryStaffInfo();
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
        //添加用户
        rlSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.UNIT_NO,unit_no);
                startActivity(UserAddStaffActivity.class,bundle,1);
            }
        });
    }


    /**
     * 查询所有员工信息
     */
    public void queryStaffInfo() {
        if (hasNetWork()) {

            if (isNotEmpty(unit_no)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("unit_no", unit_no);

                httpLoadingDialog.visible();

                HttpManager.postAsyn(HttpConstant.QUERRY_ALL_USERS_INFO, new HttpManager.ResultCallback<UserStaffManagerResp>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(UserStaffManagerResp response) {
                        if (response != null && response.getState() == 200) {
                            UserStaffManagerListAdapter adapter = new UserStaffManagerListAdapter(activity, response.getResult(),unit_no);
                            lvContent.setAdapter(adapter);
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            queryStaffInfo();
        }
    }

    @Override
    public Class<?> getClazz() {
        return UserStaffManagerActivity.class;
    }
}
