package com.sixe.dtu.vm.adapter.index.child;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.index.child.ControlGroupResp;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * 控制器分组数据
 * Created by liu on 17/3/2.
 */

public class ControlGroupListAdapter extends CommonAdapter<ControlGroupResp.GroupBean> {

    private TextView tvName;
    private LinearLayout llContent;
    private View viewLeft;

    private int lastSelect = 0;

    public ControlGroupListAdapter(Activity activity, List<ControlGroupResp.GroupBean> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_dtu_group_data_lv_item, parent, false);
        }

        tvName = get(convertView, R.id.tv_name);
        llContent = get(convertView, R.id.ll_content);
        viewLeft = get(convertView, R.id.view_left);

        tvName.setText(list.get(position).getName());

        if (lastSelect == position) {//选中
            llContent.setBackgroundResource(R.color.color_white);
            tvName.setTextColor(getRes().getColor(R.color.color_45c717));
            tvName.setTextSize(16f);
            viewLeft.setVisibility(View.VISIBLE);
        } else {//未选中
            llContent.setBackgroundResource(R.color.color_ebebeb);
            tvName.setTextColor(getRes().getColor(R.color.color_323232));
            tvName.setTextSize(14f);
            viewLeft.setVisibility(View.GONE);
        }

        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelect = position;
                notifyDataSetChanged();

                //通知更新分类显示的结果
                onClickClassify.clickClassify(list.get(position).getNode_addr());
            }
        });

        return convertView;
    }

    private OnClickClassify onClickClassify;

    public void setOnClickClassify(OnClickClassify onClickClassify) {
        this.onClickClassify = onClickClassify;
    }

    public interface OnClickClassify {
        void clickClassify(String group_id);
    }
}
