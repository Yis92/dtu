package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.interfaces.iData.ILineData;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;

import java.util.ArrayList;

/**
 * 历史数据曲线展示
 * Created by Administrator on 2017/4/1.
 */

public class HistoryDataActivity extends BaseActivity {

    private Toolbar toolbar;

    private LineData mLineData = new LineData();
    private ArrayList<ILineData> mDataList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_history_data);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);
    }

    @Override
    public void initEvents() {
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public Class<?> getClazz() {
        return HistoryDataActivity.class;
    }
}
