package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.interfaces.iData.ILineData;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.base.BaseActivity2;

import java.util.ArrayList;

/**
 * 历史数据曲线展示
 * Created by Administrator on 2017/4/1.
 */

public class HistoryDataActivity extends BaseActivity2 {

//    private Toolbar toolbar;

    protected float[][] points = new float[][]{{0, 10}, {1, 15}, {2, 20}, {3, 25}, {4, 27}, {5, 32}, {6, 38}, {7, 45}, {8, 55}, {9, 62}, {10, 31}, {11, 31}, {12, 31}, {13, 31}, {14, 31}, {15, 31}, {16, 31}, {17, 31}, {18, 31}, {19, 31}, {20, 31}, {21, 31}, {22, 31}, {23, 31}, {24, 31}};

    protected float[][] points2 = new float[][]{{1, 52}, {2, 13}, {3, 51}, {4, 20}, {5, 19}, {6, 20}, {7, 54}, {8, 7}, {9, 19}, {10, 41}, {11, 41}, {12, 41}, {13, 41}, {14, 41}, {15, 41}, {16, 41}, {17, 41}, {18, 41}, {19, 41}, {20, 41}};
    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private LineData mLineData = new LineData();
    private ArrayList<ILineData> mDataList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_history_data);
    }

    protected float pxTodp(float value) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
        return valueDP;
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
//        toolbar = findView(R.id.tool_bar);
        lineChart = findView(R.id.lineChart);
    }

    @Override
    public void initData(Intent intent) {
//        toolbar.setNavigationIcon(R.mipmap.white_back);
        initData();
        lineChart.setDataList(mDataList);
    }

    @Override
    public void initEvents() {
        //返回
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    private void initData() {
        for (int i = 0; i < points.length; i++) {
            mPointArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        mLineData.setValue(mPointArrayList);
        mLineData.setColor(Color.MAGENTA);
        mLineData.setPaintWidth(pxTodp(3));
        mLineData.setTextSize(pxTodp(10));
        mDataList.add(mLineData);
    }

    @Override
    public Class<?> getClazz() {
        return HistoryDataActivity.class;
    }
}
