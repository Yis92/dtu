package com.sixe.dtu.vm.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexAlarmInfoResp;
import com.sixe.dtu.http.entity.index.IndexSensorInfoResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.IndexAlarmInfoListAdapter;
import com.sixe.dtu.vm.adapter.index.IndexSensorInfoListAdapter;
import com.sixe.dtu.vm.index.child.UpdateAlarmInfoActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 报警信息
 * Created by liu on 17/3/7.
 */

public class IndexAlarmInfoFragment extends BaseFragment {

    private ListView listView;
    private IndexAlarmInfoListAdapter adapter;
    private List<IndexAlarmInfoResp> dataList;

    private String dtu_sh;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_alarm_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        listView = findView(R.id.listView);
    }

    @Override
    public void initData(Bundle bundle) {
        dtu_sh = bundle.getString(Constant.DTU_SN);

        queryAlarmInfo();
    }

    @Override
    public void initEvents() {
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sh);
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
            map.put("dtu_sn", dtu_sh);

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
        return IndexAlarmInfoFragment.class;
    }
}
