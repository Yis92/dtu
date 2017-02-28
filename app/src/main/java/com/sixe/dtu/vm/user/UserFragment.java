package com.sixe.dtu.vm.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;

/**
 * Created by liu on 17/2/25.
 */

public class UserFragment extends BaseFragment {
    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_user, viewGroup, false);
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
        return UserFragment.class;
    }
}
