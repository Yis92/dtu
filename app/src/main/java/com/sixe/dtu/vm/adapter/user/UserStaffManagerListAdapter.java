package com.sixe.dtu.vm.adapter.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.AppApplication;
import com.sixe.dtu.R;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserStaffManagerResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.user.UserStaffInfoActivity;
import com.sixe.dtu.vm.user.UserUpdatePasswordActivity;
import com.sixe.dtu.vm.user.UserUpdateStaffInfoActivity;
import com.sixe.dtu.vm.user.UserUpdateStaffPwdActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 员工管理adapter
 * Created by liu on 17/3/1.
 */

public class UserStaffManagerListAdapter extends CommonAdapter<UserStaffManagerResp> {

    private TextView user_id;
    private TextView user_level;
    private LinearLayout llContent;
    private Button btnMore;
    private Button btnUpdate;
    private Button btnDel;
    private Button btnUpdatePwd;

    private String unit_no;//所属单位编号

    private HttpLoadingDialog httpLoadingDialog;

    public UserStaffManagerListAdapter(Activity activity, List<UserStaffManagerResp> list, String unit_no) {
        super(activity, list);
        this.unit_no = unit_no;
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_user_staff_manager_lv_item, parent, false);
        }

        user_id = get(convertView, R.id.user_id);
        user_level = get(convertView, R.id.user_level);
        llContent = get(convertView, R.id.ll_content);
        btnMore = get(convertView, R.id.btn_more);
        btnUpdate = get(convertView, R.id.btn_update);
        btnDel = get(convertView, R.id.btn_del);
        btnUpdatePwd = get(convertView, R.id.btn_update_pwd);

        user_id.setText(list.get(position).getUser_id());

        int level = list.get(position).getUser_level();
        if (level == 10) {
            user_level.setText("管理员");
        } else if (level == 11) {
            user_level.setText("高级用户");
        } else {
            user_level.setText("普通用户");
        }

        if (position % 2 == 0) {
            llContent.setBackgroundResource(R.color.color_d5edf8);
        } else {
            llContent.setBackgroundResource(R.color.color_f5f5f5);
        }

        //查看更多
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.USER_STAFF_INFO, list.get(position));
                startActivity(UserStaffInfoActivity.class, bundle);
            }
        });

        //修改
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.USER_STAFF_INFO, list.get(position));
                startActivity(UserUpdateStaffInfoActivity.class, bundle, 1);
            }
        });

        //删除
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delStaff(list.get(position).getUser_id(), position);
            }
        });

        //修改密码
        btnUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("update_staff_pwd", list.get(position).getUser_id());
                startActivity(UserUpdateStaffPwdActivity.class, bundle);
            }
        });

        return convertView;
    }


    /**
     * 删除员工
     *
     * @param user_id 用户id
     */
    public void delStaff(String user_id, final int position) {
        if (hasNetWork()) {

            String host_user_id = AppApplication.getPreferenceHelper().getString(Constant.USER_ID, "");

            if (isNotEmpty(host_user_id)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("unit_no", unit_no);
                map.put("host_user_id", host_user_id);

                httpLoadingDialog.visible();

                HttpManager.postAsyn(HttpConstant.DEL_USER, new HttpManager.ResultCallback<CommonResponse>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(CommonResponse response) {
                        if (response != null && response.getState() == 200) {
                            showToast("删除成功");
                            list.remove(position);
                            notifyDataSetChanged();
                        } else {
                            showToast(response.getMessage());
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }
}
