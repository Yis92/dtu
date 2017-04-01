package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexControlPointResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.IndexControlPointListAdapter;
import com.sixe.dtu.vm.index.child.ControlPointTaskActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 控制节点信息
 * Created by liu on 17/3/7.
 */

public class IndexControlPointFragment extends BaseFragment {

    private ListView listView;
    private IndexControlPointListAdapter adapter;
    private List<IndexControlPointResp> dataList;
    private Button btnTask;

    private String dtu_sn;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_control_point, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        listView = findView(R.id.listView);
        btnTask = findView(R.id.btn_task);
    }

    @Override
    public void initData(Bundle bundle) {
        dtu_sn = bundle.getString(Constant.DTU_SN);

        //普通用户不可以修改dtu信息
        int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, 12);
        if (user_level != 12) {
            btnTask.setVisibility(View.VISIBLE);
        }

        queryControlPointInfo();
    }

    @Override
    public void initEvents() {
        //控制节点任务状态
        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                startActivity(ControlPointTaskActivity.class, bundle);
            }
        });
    }

    /**
     * 查询控制节点信息
     */
    public void queryControlPointInfo() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_CTRL_NODE_INFO, new HttpManager.ResultCallback<IndexControlPointResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(IndexControlPointResp response) {
                    if (response != null && response.getState() == 200) {
                        dataList = response.getResult();
                        adapter = new IndexControlPointListAdapter(activity, dataList);
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
        return IndexControlPointFragment.class;
    }
}
