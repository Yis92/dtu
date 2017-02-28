package com.sixe.dtu.vm.adapter.user;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.user.UserCompanyInfoResp;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 单位信息adapter
 * Created by liu on 17/2/28.
 */

public class UserCompanyInfoListAdapter extends CommonAdapter<UserCompanyInfoResp.UserCompanyInfo> {

    private TextView tvName;
    private TextView tvValue;
    private LinearLayout llContent;

    public UserCompanyInfoListAdapter(Activity activity, List<UserCompanyInfoResp.UserCompanyInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_user_cpmpany_info_lv_item, parent, false);
        }

        tvName = findView(convertView, R.id.tv_name);
        tvValue = findView(convertView, R.id.tv_value);
        llContent = findView(convertView, R.id.ll_content);

        tvName.setText(list.get(position).getName());
        tvValue.setText(list.get(position).getValue());

        if (position % 2 == 0) {
            llContent.setBackgroundColor(getColor(R.color.color_d5edf8));
        } else {
            llContent.setBackgroundColor(getColor(R.color.color_f5f5f5));
        }


        return convertView;
    }
}
