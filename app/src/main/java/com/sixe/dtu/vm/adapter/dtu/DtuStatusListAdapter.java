package com.sixe.dtu.vm.adapter.dtu;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * dtu实时数据
 * Created by liu on 17/3/2.
 */

public class DtuStatusListAdapter extends CommonAdapter<List<String>> {

    private LinearLayout llContent;
    private TextView tvYaosu;
    private TextView tvValue;
    private TextView tvState;

    public DtuStatusListAdapter(Activity activity, List<List<String>> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_dtu_status_lv_item, parent, false);
        }

        llContent = get(convertView, R.id.ll_content);
        tvYaosu = get(convertView, R.id.tv_yaosu);
        tvValue = get(convertView, R.id.tv_value);
        tvState = get(convertView, R.id.tv_state);

        tvYaosu.setText(list.get(position).get(0));
        tvValue.setText(list.get(position).get(1));
        tvState.setText(list.get(position).get(2));

        if (position % 2 == 0) {
            llContent.setBackgroundResource(R.color.color_f5f5f5);
        } else {
            llContent.setBackgroundResource(R.color.color_d9f3d8);
        }

        return convertView;
    }
}
