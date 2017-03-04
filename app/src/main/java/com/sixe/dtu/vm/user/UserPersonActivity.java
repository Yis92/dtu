package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserPersonResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 我的信息
 * Created by liu on 17/2/28.
 */

public class UserPersonActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tv_user_id;
    private TextView tv_level;
    private TextView tv_all_name;
    private TextView tv_describe;
    private TextView tv_tel1;
    private TextView tv_tel2;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_person);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        tv_user_id = findView(R.id.tv_user_id);
        tv_level = findView(R.id.tv_level);
        tv_all_name = findView(R.id.tv_all_name);
        tv_describe = findView(R.id.tv_describe);
        tv_tel1 = findView(R.id.tv_tel1);
        tv_tel2 = findView(R.id.tv_tel2);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.inflateMenu(R.menu.userinfo);
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
//        UserCompanyInfoListAdapter adapter = new UserCompanyInfoListAdapter(activity, list);
//        lvContent.setAdapter(adapter);
    }

    @Override
    public void initEvents() {
        //修改资料、修改密码
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.update_data:
                        startActivity(UserUpdatePersonActivity.class, 1);
                        break;
                    case R.id.update_password:
                        startActivity(UserUpdatePasswordActivity.class);
                        break;
                }
                return true;
            }
        });
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            queryUserInfo();
        }
    }

    /**
     * 查询用户信息
     */
    public void queryUserInfo() {

        if (hasNetWork()) {

            String user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

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
                            tv_user_id.setText(resp.getUser_id());

                            int level = resp.getUser_level();
                            if (level == 10) {
                                tv_level.setText("管理员");
                            } else if (level == 11) {
                                tv_level.setText("高级用户");
                            } else {
                                tv_level.setText("普通用户");
                            }

                            tv_all_name.setText(resp.getUser_full_name());
                            tv_describe.setText(resp.getUser_describ());
                            tv_tel1.setText(resp.getUser_tel1());
                            tv_tel2.setText(resp.getUser_tel2());
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }

    @Override
    public Class<?> getClazz() {
        return UserPersonActivity.class;
    }
}
