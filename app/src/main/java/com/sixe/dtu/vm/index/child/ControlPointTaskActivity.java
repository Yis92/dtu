package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.child.ControlPointTaskResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.child.ControlPointTaskListAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 控制节点任务状态
 * Created by Administrator on 2017/4/1.
 */

public class ControlPointTaskActivity extends BaseActivity {

    private Toolbar toolbar;
    private ListView listView;
    private ControlPointTaskListAdapter adapter;
    private List<ControlPointTaskResp> dataList;

    private String dtu_sn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_control_point_task);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        listView = findView(R.id.listView);
    }

    @Override
    public void initData(Intent intent) {

        dtu_sn = intent.getExtras().getString(Constant.DTU_SN);

        toolbar.setNavigationIcon(R.mipmap.white_back);

        queryControlPointTask();

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

    /**
     * 查询控制节点任务状态
     */
    public void queryControlPointTask() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);
            map.put("node_addr", "3");

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_CTRL_NODE_TASK, new HttpManager.ResultCallback<ControlPointTaskResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(ControlPointTaskResp response) {
                    if (response != null && response.getState() == 200) {
                        dataList = response.getResult();
                        adapter = new ControlPointTaskListAdapter(activity, dataList);
                        listView.setAdapter(adapter);
                    }
                }
            }, map);
        } else {
            showNetWorkError();
        }
    }

    @Override
    public Class<?> getClazz() {
        return ControlPointTaskActivity.class;
    }
}
