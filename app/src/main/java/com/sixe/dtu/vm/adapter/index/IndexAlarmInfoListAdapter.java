package com.sixe.dtu.vm.adapter.index;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexAlarmInfoResp;
import com.sixe.dtu.vm.index.child.UpdateAlarmInfoActivity;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 报警信息
 * Created by Administrator on 2017/3/30.
 */

public class IndexAlarmInfoListAdapter extends CommonAdapter<IndexAlarmInfoResp> {

    private LinearLayout llContent;
    private TextView name;
    private TextView describ;
    private TextView up;
    private TextView low;
    private TextView lasting;
    private TextView interval;
    private TextView enable;

    public IndexAlarmInfoListAdapter(Activity activity, List<IndexAlarmInfoResp> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_index_alarm_info_lv_item, viewGroup, false);
        }

        llContent = get(view, R.id.ll_content);
        name = get(view, R.id.name);
        describ = get(view, R.id.describ);
        up = get(view, R.id.up);
        low = get(view, R.id.low);
        lasting = get(view, R.id.lasting);
        interval = get(view, R.id.interval);
        enable = get(view, R.id.enable);

        if (i % 2 == 0) {
            llContent.setBackgroundResource(R.drawable.shape_sensor_one);
        } else {
            llContent.setBackgroundResource(R.drawable.shape_sensor_two);
        }

        name.setText(list.get(i).getName());
        describ.setText(list.get(i).getDescrib());
        up.setText(list.get(i).getUp());
        low.setText(list.get(i).getLow());
        lasting.setText(list.get(i).getLasting());
        interval.setText(list.get(i).getInterval());
        enable.setText(list.get(i).getEnable());

        return view;
    }
}
