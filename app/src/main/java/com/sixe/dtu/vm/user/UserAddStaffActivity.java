package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserAddUserQueryCompanyResp;
import com.sixe.dtu.http.entity.user.UserStaffManagerResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserStaffSpinnerAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 添加员工
 * Created by liu on 17/3/1.
 */

public class UserAddStaffActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText user_id;
    private EditText pwd;
    private Spinner unit_id;
    private Spinner user_level;
    private EditText user_full_name;
    private EditText user_describ;
    private Button btnSubmit;

    private UserAddUserQueryCompanyResp resp;

    private String selectUnitId;//选中的单位id
    private String selectUserLevel;//选中的用户等级

    private String unit_no;//单位编号

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_add_staff);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        user_id = findView(R.id.user_id);
        pwd = findView(R.id.pwd);
        unit_id = findView(R.id.unit_id);
        user_level = findView(R.id.user_level);
        user_full_name = findView(R.id.user_full_name);
        user_describ = findView(R.id.user_describ);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        unit_no = intent.getExtras().getString(Constant.UNIT_NO);

        String[] userLevelList = new String[]{"高级用户", "普通用户"};
        UserStaffSpinnerAdapter adapter = new UserStaffSpinnerAdapter(activity, userLevelList);
        user_level.setAdapter(adapter);

        //查询单位信息
        queryCompanyInfo();
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
        //提交
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStaff();
            }
        });
        //监听单位选择Spinner选择
        unit_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectUnitId = resp.getResult().get(position).getUnit_no();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //监听用户等级Spinner选择
        user_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectUserLevel = "11";
                } else {
                    selectUserLevel = "12";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 查询单位信息
     */
    public void queryCompanyInfo() {
        if (hasNetWork()) {

            String user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

            if (isNotEmpty(user_id)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);

                httpLoadingDialog.visible();

                HttpManager.postAsyn(HttpConstant.QUERT_COMPANY_INFO, new HttpManager.ResultCallback<UserAddUserQueryCompanyResp>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(UserAddUserQueryCompanyResp response) {
                        if (response != null && response.getState() == 200) {
                            resp = response;
                            int size = response.getResult().size();
                            String[] units = new String[size];
                            for (int i = 0; i < size; i++) {
                                units[i] = response.getResult().get(i).getUnit_name();
                            }
                            UserStaffSpinnerAdapter adapter = new UserStaffSpinnerAdapter(activity, units);
                            unit_id.setAdapter(adapter);
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }

    /**
     * 添加员工
     */
    public void addStaff() {
        if (hasNetWork()) {

            String host_user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

            if (isNotEmpty(host_user_id)) {


                if (isEmpty(user_id)) {
                    showToast("请输入用户名");
                    return;
                }

                if (isEmpty(pwd)) {
                    showToast("请输入密码");
                    return;
                }

//                if (isEmpty(selectUnitId)) {
//                    showToast("请选择所属单位");
//                    return;
//                }

                if (isEmpty(selectUserLevel)) {
                    showToast("请选择用户等级");
                    return;
                }

                if (isEmpty(user_full_name)) {
                    showToast("请输入全名");
                    return;
                }

                if (isEmpty(user_describ)) {
                    showToast("请输入描述");
                    return;
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("host_user_id", host_user_id);
                map.put("user_id", getText(user_id));
                map.put("pwd", getText(pwd));
                map.put("unit_no", unit_no);
                map.put("user_level", selectUserLevel);
                map.put("user_full_name", getText(user_full_name));
                map.put("user_describ", getText(user_describ));

                httpLoadingDialog.visible();

                HttpManager.postAsyn(HttpConstant.ADD_USER, new HttpManager.ResultCallback<CommonResponse>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(CommonResponse response) {
                        if (response != null && response.getState() == 200) {
                            showToast("添加成功");
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
        return UserAddStaffActivity.class;
    }
}
