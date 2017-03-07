package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;

/**
 * dtu信息 详情
 * Created by liu on 17/3/6.
 */

public class IndexDtuInfoFragment extends BaseFragment {


    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_dtu_info, viewGroup, false);
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
        return IndexDtuInfoFragment.class;
    }
}
