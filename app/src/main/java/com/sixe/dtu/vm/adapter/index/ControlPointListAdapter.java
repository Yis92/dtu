package com.sixe.dtu.vm.adapter.index;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.index.IndexControlPointResp;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 控制节点ListView适配器
 * Created by Administrator on 2017/3/31.
 */

public class ControlPointListAdapter extends CommonAdapter<IndexControlPointResp> {

    private LinearLayout llContent;
    private TextView name;
    private TextView cfg;
    private TextView addr;
    private TextView describ;
    private TextView x;
    private TextView y;
    private TextView tsknum;


    public ControlPointListAdapter(Activity activity, List<IndexControlPointResp> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_index_control_point_lv_item, viewGroup, false);
        }

        llContent = findView(view, R.id.ll_content);
        name = findView(view, R.id.name);
        cfg = findView(view, R.id.cfg);
        addr = findView(view, R.id.addr);
        describ = findView(view, R.id.describ);
        x = findView(view, R.id.x);
        y = findView(view, R.id.y);
        tsknum = findView(view, R.id.tsknum);

        name.setText(list.get(position).getName());
        cfg.setText(list.get(position).getCfg());
        addr.setText(list.get(position).getAddr());
        describ.setText(list.get(position).getDescrib());
        x.setText(list.get(position).getX());
        y.setText(list.get(position).getY());
        tsknum.setText(list.get(position).getTsknum());

        if (position % 2 == 0) {
            llContent.setBackgroundResource(R.drawable.shape_sensor_one);
        } else {
            llContent.setBackgroundResource(R.drawable.shape_sensor_two);
        }

        int size = list.get(position).getTskdescrib().size();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            TextView textView = new TextView(activity);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 40;

            textView.setTextSize(14f);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setText(list.get(position).getTskdescrib().get(i).getTsk_describ());
            textView.setLineSpacing(0, 1.3f);
            textView.setLayoutParams(layoutParams);
//            layoutParams.setMargins(0, 20, 0, 20);

        }

        return view;
    }
}
