package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserStaffManagerResp;

/**
 * 员工信息
 * Created by liu on 17/3/1.
 */

public class UserStaffInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView user_id;
    private TextView user_level;
    private TextView user_full_name;
    private TextView user_describ;
    private TextView user_tel1;
    private TextView user_tel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_staff_info);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        user_id = findView(R.id.user_id);
        user_level = findView(R.id.user_level);
        user_full_name = findView(R.id.user_full_name);
        user_describ = findView(R.id.user_describ);
        user_tel1 = findView(R.id.user_tel1);
        user_tel2 = findView(R.id.user_tel2);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        UserStaffManagerResp resp = (UserStaffManagerResp) intent.getExtras().getSerializable(Constant.USER_STAFF_INFO);
        int level = resp.getUser_level();

        user_id.setText(resp.getUser_id());
        if (level == 10) {
            user_level.setText("管理员");
        } else if (level == 11) {
            user_level.setText("高级用户");
        } else {
            user_level.setText("普通用户");
        }
        user_full_name.setText(resp.getUser_describ());
        user_describ.setText(resp.getUser_describ());
        user_tel1.setText(resp.getUser_tel1());
        user_tel2.setText(resp.getUser_tel2());
    }

    @Override
    public void initEvents() {
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public Class<?> getClazz() {
        return UserStaffInfoActivity.class;
    }
}
