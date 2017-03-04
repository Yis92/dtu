package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserPersonResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 修改我的信息
 * Created by liu on 17/2/28.
 */

public class UserUpdatePersonActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText et_all_name;
    private EditText et_describe;
    private EditText et_tel1;
    private EditText et_tel2;

    private String user_id;
    private Button btnSubmit;


    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_update_person);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        et_all_name = findView(R.id.et_all_name);
        et_describe = findView(R.id.et_describe);
        et_tel1 = findView(R.id.et_tel1);
        et_tel2 = findView(R.id.et_tel2);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);//设置导航栏图标


        queryUserInfo();

//        List<UserCompanyInfoResp.UserCompanyInfo> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            UserCompanyInfoResp.UserCompanyInfo info = new UserCompanyInfoResp().new UserCompanyInfo();
//            info.setName("name");
//            info.setValue("value");
//            list.add(info);
//        }
//
//        UserUpdateCompanyListAdapter adapter = new UserUpdateCompanyListAdapter(activity, list);
//        lvContent.setAdapter(adapter);
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
                //表单空验证
                if (isEmpty(et_all_name)) {
                    showToast("请输入用户全名");
                    return;
                }

                if (isEmpty(et_describe)) {
                    showToast("请输入用户描述");
                    return;
                }

                if (isEmpty(et_tel1)) {
                    showToast("请输入电话1");
                    return;
                }

                if (isEmpty(et_tel2)) {
                    showToast("请输入电话2");
                    return;
                }

                //
                updateUserInfo();
            }
        });
    }


    /**
     * 查询用户信息
     */
    public void queryUserInfo() {

        if (hasNetWork()) {

            user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

            if (isNotEmpty(user_id)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);

                httpLoadingDialog.visible();

                HttpManager.postAsyn(HttpConstant.QUERY_USER_INFO, new HttpManager.ResultCallback<UserPersonResp>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(UserPersonResp response) {
                        if (isNotEmpty(response) && response.getState() == 200) {
                            UserPersonResp resp = response.getResult();
                            et_all_name.setText(resp.getUser_full_name());
                            et_describe.setText(resp.getUser_describ());
                            et_tel1.setText(resp.getUser_tel1());
                            et_tel2.setText(resp.getUser_tel2());
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }


    public void updateUserInfo() {

        if (hasNetWork()) {

            if (isNotEmpty(user_id)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("user_full_name", getText(et_all_name));
                map.put("user_full_name", getText(et_all_name));
                map.put("user_describ", getText(et_describe));
                map.put("user_tel1", getText(et_tel1));
                map.put("user_tel2", getText(et_tel2));

                httpLoadingDialog.visible("修改中...");

                HttpManager.postAsyn(HttpConstant.UPDATE_USER_INFO, new HttpManager.ResultCallback<CommonResponse>() {
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
        return UserUpdatePersonActivity.class;
    }
}
