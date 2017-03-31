package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexAlarmInfoResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.IndexAlarmInfoListAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 报警信息- 所有的报警消息或者未处理过的报警消息
 * Created by Administrator on 2017/3/31.
 */

public class AlarmInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private ListView listView;
    private IndexAlarmInfoListAdapter adapter;
    private List<IndexAlarmInfoResp> dataList;

    private String dtu_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_alarm_info);
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
        toolbar.setNavigationIcon(R.mipmap.white_back);

        dtu_sn = intent.getExtras().getString(Constant.DTU_SN);

        queryAlarmInfo();
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
        //修改报警信息
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                bundle.putSerializable(Constant.ALARM_INFO, dataList.get(i));
                startActivity(UpdateAlarmInfoActivity.class, bundle, 100);
            }
        });
    }


    /**
     * 查看报警信息，用于设置报警信息参数
     */
    public void queryAlarmInfo() {
        if (hasNetWork()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_SENSOR_WARNING_INFO, new HttpManager.ResultCallback<IndexAlarmInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {
                }

                @Override
                public void onResponse(IndexAlarmInfoResp response) {
                    if (response != null && response.getState() == 200) {
                        dataList = response.getResult();
                        adapter = new IndexAlarmInfoListAdapter(activity, dataList);
                        listView.setAdapter(adapter);
                    }
                }
            }, map);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 200) {
            queryAlarmInfo();
        }
    }

    @Override
    public Class<?> getClazz() {
        return AlarmInfoActivity.class;
    }
}
