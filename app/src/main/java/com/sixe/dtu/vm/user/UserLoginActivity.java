package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.MainActivity;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 登录页面
 * Created by gzg on 2016/3/28.
 */
public class UserLoginActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private TextView tvTitle;
    private Button btnLogin;
    private EditText etPhone, etPassword;
    private CheckBox cbAgree;
    private ImageView ivClear1;
    private String password, mobile;
    private HttpLoadingDialog httpLoadingDialog;//加载进度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        rlBack = findView(R.id.rl_back);
        tvTitle = findView(R.id.tv_title);
        btnLogin = findView(R.id.btn_login);
        etPassword = findView(R.id.et_password);
        cbAgree = findView(R.id.cb_agree);
        etPhone = findView(R.id.et_phone);
        ivClear1 = findView(R.id.iv_clear1);
    }

    @Override
    public void initData(Intent intent) {
        tvTitle.setText("登录");

        boolean isRemember = getPreferenceHelper().getBoolean(Constant.REMEMBER_PASSWORD, false);
        if (isRemember) {
            cbAgree.setChecked(true);
            etPhone.setText(getPreferenceHelper().getString(Constant.USER_NAME, ""));
            etPassword.setText(getPreferenceHelper().getString(Constant.PASSWORD, ""));
        }
    }

    @Override
    public void initEvents() {

        //返回按钮的点击事件，回退到上一个activity
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //CheckedB监听是否选中
        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    getPreferenceHelper().putBoolean(Constant.REMEMBER_PASSWORD, true);
                } else {
                    getPreferenceHelper().putBoolean(Constant.REMEMBER_PASSWORD, false);
                }
            }
        });

        //登录按钮点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = getText(etPassword);
                mobile = getText(etPhone);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }

                if (isEmpty(mobile)) {
                    showToast("请输入用户名");
                    return;
                }

                if (isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }

                //记住用户名密码
                if (cbAgree.isChecked()) {
                    getPreferenceHelper().putBoolean(Constant.REMEMBER_PASSWORD, true);
                    getPreferenceHelper().putString(Constant.USER_NAME, mobile);
                    getPreferenceHelper().putString(Constant.PASSWORD, password);
                }

                HashMap<String, String> map = new HashMap<>();

                map.put("user_id", mobile);
                map.put("pwd", password);

                httpLoadingDialog.visible("登录中...");

                HttpManager.postAsyn(HttpConstant.LOGIN, new HttpManager.ResultCallback<UserLoginResp>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(UserLoginResp response) {
                        if (response != null) {
                            if (response.getState() == 200) {
                                //将用户等级存在本地
                                getPreferenceHelper().putInt(Constant.USER_LEVEL, response.getResult().getUser_level());
                                //将用户id存入到本地
                                getPreferenceHelper().putString(Constant.USER_ID, response.getResult().getUser_id());

                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constant.USER_INFO, response);
                                startActivity(MainActivity.class, bundle);
                                finish();
                            } else {
                                showToast(response.getMessage());
                            }
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);


            }
        });

        //检测edittext是否有输入，显示一键清除
        editClear(etPhone, ivClear1);
        ivClear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhone.setText("");
            }
        });
    }


    //自定义方法减少重复代码，用于检测edittext是否有输入
    public void editClear(final EditText editClear, final ImageView ivClear) {
        editClear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editClear.getText().toString() != null && !editClear.getText().toString().equals("")) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public Class<?> getClazz() {
        return UserLoginActivity.class;
    }
}
