package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserStaffManagerResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserStaffSpinnerAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 修改员工信息
 * Created by liu on 17/3/1.
 */

public class UserUpdateStaffInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private Spinner user_level;
    private EditText user_full_name;
    private EditText user_describ;
    private EditText user_tel1;
    private EditText user_tel2;
    private Button btnSubmit;

    private String user_id;
    private int level;//用户等级

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_update_user_staff_info);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        user_level = findView(R.id.user_level);
        user_full_name = findView(R.id.user_full_name);
        user_describ = findView(R.id.user_describ);
        user_tel1 = findView(R.id.user_tel1);
        user_tel2 = findView(R.id.user_tel2);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        UserStaffManagerResp resp = (UserStaffManagerResp) intent.getExtras().getSerializable(Constant.USER_STAFF_INFO);

        user_id = resp.getUser_id();
        level = resp.getUser_level();

        String[] list = {"高级用户", "普通用户"};
        UserStaffSpinnerAdapter adapter = new UserStaffSpinnerAdapter(activity, list);
        user_level.setAdapter(adapter);

        if (level == 11) {
            user_level.setSelection(0);
        } else {
            user_level.setSelection(1);
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
        //修改
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(user_level)) {
                    showToast("请用户等级");
                    return;
                }

                if (isEmpty(user_full_name)) {
                    showToast("请输入确认密码");
                    return;
                }

                if (isEmpty(user_describ)) {
                    showToast("请输入用户描述");
                    return;
                }

                if (isEmpty(user_tel1)) {
                    showToast("请电话1");
                    return;
                }

                if (isEmpty(user_tel1)) {
                    showToast("请电话2");
                    return;
                }

                updateStaffInfo();
            }
        });
        //
        user_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    level = 11;
                } else {
                    level = 12;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * 修改员工密码
     */
    public void updateStaffInfo() {
        if (hasNetWork()) {

            String host_user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

            HashMap<String, String> map = new HashMap();

            if (isNotEmpty(user_id) && isNotEmpty(host_user_id)) {
                map.put("host_user_id", host_user_id);//管理员id
                map.put("user_id", user_id);
                map.put("user_level", level + "");
                map.put("user_full_name", getText(user_full_name));
                map.put("user_describ", getText(user_describ));
                map.put("user_tel1", getText(user_tel1));
                map.put("user_tel2", getText(user_tel2));

                httpLoadingDialog.visible("修改中...");

                HttpManager.postAsyn(HttpConstant.UPDATE_USER_INFO_BY_HOST, new HttpManager.ResultCallback<CommonResponse>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(CommonResponse response) {
                        if (response != null && response.getState() == 200) {
                            showToast("修改成功");
                            setResult(RESULT_OK);
                            finish();
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
        return UserUpdateStaffInfoActivity.class;
    }
}
