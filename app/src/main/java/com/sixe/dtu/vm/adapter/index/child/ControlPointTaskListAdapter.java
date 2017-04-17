package com.sixe.dtu.vm.adapter.index.child;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.index.IndexAlarmInfoResp;
import com.sixe.dtu.http.entity.index.child.ControlGroupResp;
import com.sixe.dtu.http.entity.index.child.ControlPointTaskResp;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 控制节点任务状态
 * Created by Administrator on 2017/3/30.
 */

public class ControlPointTaskListAdapter extends CommonAdapter<ControlGroupResp.TskinfoBean.TskBean> {

    private LinearLayout llContent;
    private TextView tsk_channel;//任务通道号 1-8
    private TextView tsk_describ;//通道描述
    private TextView tsk_type;//任务类型 0：立即关闭，1：立即打开，2：计划打开，3：没有任务
    private TextView tsk_dt;//任务执行日期
    private TextView tsk_tm;//任务执行时间
    private TextView tsk_second;//任务执行周期 秒
    private TextView tsk_surplus;//任务剩余时间
    private TextView state;//状态 1：正常，0：故障

    public ControlPointTaskListAdapter(Activity activity, List<ControlGroupResp.TskinfoBean.TskBean> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_control_point_task_lv_item, viewGroup, false);
        }

        llContent = get(view, R.id.ll_content);
        tsk_channel = get(view, R.id.tsk_channel);
        tsk_describ = get(view, R.id.tsk_describ);
        tsk_type = get(view, R.id.tsk_type);
        tsk_dt = get(view, R.id.tsk_dt);
        tsk_tm = get(view, R.id.tsk_tm);
        tsk_second = get(view, R.id.tsk_second);
        tsk_surplus = get(view, R.id.tsk_surplus);
        state = get(view, R.id.state);

        if (i % 2 == 0) {
            llContent.setBackgroundResource(R.drawable.shape_sensor_one);
        } else {
            llContent.setBackgroundResource(R.drawable.shape_sensor_two);
        }

        tsk_channel.setText(list.get(i).getTsk_channel());
        tsk_describ.setText(list.get(i).getTsk_describ());

        String tskType = list.get(i).getTsk_type();
        if (tskType.equals("0")) {
            tsk_type.setText("立即关闭");
        } else if (tskType.equals("1")) {
            tsk_type.setText("立即打开");
        } else if (tskType.equals("2")) {
            tsk_type.setText("计划打开");
        } else if (tskType.equals("3")) {
            tsk_type.setText("没有任务");
        }

        tsk_dt.setText(list.get(i).getTsk_dt());
        tsk_tm.setText(list.get(i).getTsk_tm());
        tsk_second.setText(list.get(i).getTsk_second());
        tsk_surplus.setText(list.get(i).getTsk_surplus());

        String tskStatus = list.get(i).get_$Tsk_status252();
        if (tskStatus.equals("1")) {
            state.setText("正常");
        } else {
            state.setText("故障");
        }

        return view;
    }
}
