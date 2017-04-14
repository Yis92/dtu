package com.sixe.dtu.vm.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ProgressBarDemoActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState, int layoutResID) {
        super.onCreate(savedInstanceState, layoutResID);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        recyclerView = findView(R.id.recycle_view);
    }

    @Override
    public void initData(Intent intent) {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public Class<?> getClazz() {
        return null;
    }
}
