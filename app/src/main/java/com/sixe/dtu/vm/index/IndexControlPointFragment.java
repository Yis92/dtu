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
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 控制节点信息
 * Created by liu on 17/3/7.
 */

public class IndexControlPointFragment extends BaseFragment {

    private ListView listView;
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
        queryControlPointInfo();
    }

    @Override
    public void initEvents() {

    }

    /**
     * 查询控制节点信息
     */
    public void queryControlPointInfo() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_CTRL_NODE_INFO, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(CommonResponse response) {

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
