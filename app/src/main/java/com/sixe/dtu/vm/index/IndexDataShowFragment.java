package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;

/**
 * 数据显示
 * Created by liu on 17/3/7.
 */

public class IndexDataShowFragment extends BaseFragment {
    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_data_show, viewGroup, false);
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
        return IndexDataShowFragment.class;
    }
}
