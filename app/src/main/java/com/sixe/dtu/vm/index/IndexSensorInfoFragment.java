package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;

/**
 * 传感器节点信息
 * Created by liu on 17/3/7.
 */

public class IndexSensorInfoFragment extends BaseFragment {


    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_sensor_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public Class<?> getClazz() {
        return IndexSensorInfoFragment.class;
    }
}
