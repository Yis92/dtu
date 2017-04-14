package com.sixe.dtu.vm.adapter.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.vm.user.UserDtuInfoActivity;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * Created by Administrator on 2017/4/14.
 */

public class UserHomeGridAdapter extends CommonAdapter<UserLoginResp.Company.DtuName> {

    private TextView tvDtuName;
    private LinearLayout llContent;

    public UserHomeGridAdapter(Activity activity, List<UserLoginResp.Company.DtuName> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_user_home_gv_item, viewGroup, false);
        }

        tvDtuName = get(view, R.id.tv_dtu_name);
        llContent = get(view, R.id.ll_content);

        tvDtuName.setText(list.get(i).getDtu_name());

        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, list.get(i).getDtu_sn());
                bundle.putString(Constant.DTU_NAME, list.get(i).getDtu_name());
                startActivity(UserDtuInfoActivity.class, bundle);
            }
        });

        return view;
    }
}
