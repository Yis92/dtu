package com.sixe.dtu.vm.adapter.dtu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.dtu.DtuAllInfoResp;
import com.sixe.dtu.vm.dtu.info.DtuDataInfoActivity;
import com.sixe.dtu.vm.dtu.info.DtuStatusActivity;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 查看改公司所有dtu信息
 * Created by liu on 17/3/2.
 */

public class DtuAllInfoListAdapter extends CommonAdapter<DtuAllInfoResp> {

    private TextView tvDtuName;
    private Button btnInfo;
    private Button btnStatus;
    private LinearLayout llContent;

    public DtuAllInfoListAdapter(Activity activity, List<DtuAllInfoResp> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_dtu_all_info_lv_item, parent, false);
        }

        tvDtuName = get(convertView, R.id.tv_dtu_name);
        btnInfo = get(convertView, R.id.btn_info);
        btnStatus = get(convertView, R.id.btn_status);
        llContent = get(convertView, R.id.ll_content);

        String name = list.get(position).getDtu_name();

        if (isNotEmpty(name)) {
            tvDtuName.setText(name);
        }

        if (position % 2 == 0) {
            llContent.setBackgroundResource(R.color.color_f5f5f5);
        } else {
            llContent.setBackgroundResource(R.color.color_d5edf8);
        }

        //数据
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, list.get(position).getDtu_sn());
                startActivity(DtuDataInfoActivity.class, bundle);
            }
        });

        //状态
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, list.get(position).getDtu_sn());
                startActivity(DtuStatusActivity.class, bundle);
            }
        });
        return convertView;
    }
}
