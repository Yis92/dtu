package com.sixe.dtu.vm.adapter.index.child;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.index.IndexAlarmInfoResp;
import com.sixe.dtu.http.entity.index.child.AlarmInfoResp;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 报警信息
 * Created by Administrator on 2017/3/30.
 */

public class AlarmInfoListAdapter extends CommonAdapter<AlarmInfoResp> {

    private LinearLayout llContent;
    private TextView msg;
    private TextView tm;
    private TextView btnOperation;//处理报警信息

    public AlarmInfoListAdapter(Activity activity, List<AlarmInfoResp> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_alarm_info_lv_item, viewGroup, false);
        }

        llContent = get(view, R.id.ll_content);
        msg = get(view, R.id.msg);
        tm = get(view, R.id.tm);
        btnOperation = get(view, R.id.btn_operation);

        if (list.get(i).getDispose().equals("0")) {
            llContent.setBackgroundResource(R.drawable.shape_sensor_one);
            btnOperation.setBackgroundResource(R.drawable.shape_btn_red);
            btnOperation.setText("处理报警信息");

            //处理报警信息
            btnOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickOperationAlarm.onClickOperationAlarm(i);
                }
            });
        } else {
            btnOperation.setBackgroundResource(R.drawable.shape_btn_gray);
            btnOperation.setText("已处理");
            llContent.setBackgroundResource(R.drawable.shape_sensor_three);
        }

        msg.setText(list.get(i).getMsg());
        tm.setText(list.get(i).getTm());

        return view;
    }

    private OnClickOperationAlarm onClickOperationAlarm;

    public void setOnClickOperationAlarm(OnClickOperationAlarm onClickOperationAlarm) {
        this.onClickOperationAlarm = onClickOperationAlarm;
    }

    public interface OnClickOperationAlarm {
        void onClickOperationAlarm(int position);
    }
}
