package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 修改员工密码
 * Created by liu on 17/3/1.
 */

public class UserUpdateStaffPwdActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText etPwd2;//新密码
    private EditText etPwd3;//确认密码

    private String user_id;

    private Button btnSubmit;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_update_staff_pwd);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        etPwd2 = findView(R.id.et_pwd2);
        etPwd3 = findView(R.id.et_pwd3);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        if (intent.getExtras() != null) {
            user_id = intent.getExtras().getString("update_staff_pwd");
        }
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
        //修改密码
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

    }

    /**
     * 修改密码
     */
    public void updatePassword() {

        if (hasNetWork()) {

            String host_user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

            if (isNotEmpty(user_id) && isNotEmpty(host_user_id)) {

                String pwd2 = getText(etPwd2);
                String pwd3 = getText(etPwd3);

                if (isEmpty(pwd2)) {
                    showToast("请输入新密码");
                    return;
                }

                if (isEmpty(pwd3)) {
                    showToast("请输入确认密码");
                    return;
                }

                if (!pwd2.equals(pwd3)) {
                    showToast("两次密码不一致");
                    return;
                }

                HashMap<String, String> map = new HashMap<>();

                map.put("user_id", user_id);
                map.put("host_user_id", host_user_id);
                map.put("pwd_new", pwd3);

                httpLoadingDialog.visible("修改中...");

                HttpManager.postAsyn(HttpConstant.UPDATE_USER_PWD_BY_HOST, new HttpManager.ResultCallback<CommonResponse>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(CommonResponse response) {
                        if (response != null && response.getState() == 200) {
                            etPwd2.setText("");
                            etPwd3.setText("");
                            showToast("修改成功");
                        } else {
                            showToast(response.getMessage());
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }

    }

    @Override
    public Class<?> getClazz() {
        return UserUpdateStaffPwdActivity.class;
    }
}
