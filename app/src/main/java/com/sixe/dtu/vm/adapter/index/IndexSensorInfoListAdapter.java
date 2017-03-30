package com.sixe.dtu.vm.adapter.index;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.index.IndexSensorInfoResp;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 传感器节点listview适配器
 * Created by Administrator on 2017/3/30.
 */

public class IndexSensorInfoListAdapter extends CommonAdapter<IndexSensorInfoResp> {

    private LinearLayout llContent;

    private TextView name;//节点名称
    private TextView cfg;//节点类型
    private TextView addr;//节点地址
    private TextView describ;//节点描述
    private TextView x;//x坐标
    private TextView y;//y坐标

    public IndexSensorInfoListAdapter(Activity activity, List<IndexSensorInfoResp> list) {
        super(activity, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_index_sensor_info_lv_item, viewGroup, false);
        }

        llContent = get(view,R.id.ll_content);
        name = get(view,R.id.name);
        cfg = get(view,R.id.cfg);
        addr = get(view,R.id.addr);
        describ = get(view,R.id.describ);
        x = get(view,R.id.x);
        y = get(view,R.id.y);

        if (i%2==0) {
            llContent.setBackgroundResource(R.drawable.shape_sensor_one);
        }else {
            llContent.setBackgroundResource(R.drawable.shape_sensor_two);
        }

        name.setText(list.get(i).getName());
        cfg.setText(list.get(i).getCfg());
        addr.setText(list.get(i).getAddr());
        describ.setText(list.get(i).getDescrib());
        x.setText(list.get(i).getX());
        y.setText(list.get(i).getY());

        return view;
    }
}
