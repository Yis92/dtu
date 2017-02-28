package com.sixe.dtu.vm.test;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sixe.dtu.R;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * Created by liu on 17/2/27.
 */

public class UserTestAdapter extends CommonAdapter<String> {

    private TextView tvText;

    public UserTestAdapter(Activity activity, List<String> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_user_test,parent,false);
        }

        tvText = get(convertView,R.id.tv_text);

        tvText.setText(list.get(position));

        return convertView;
    }
}
