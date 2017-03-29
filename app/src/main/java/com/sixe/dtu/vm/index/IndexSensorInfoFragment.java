package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.http.entity.index.IndexDtuInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 传感器节点信息
 * Created by liu on 17/3/7.
 */

public class IndexSensorInfoFragment extends BaseFragment {

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_sensor_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData(Bundle bundle) {
        http();
    }

    @Override
    public void initEvents() {

    }

    /**
     * 查询dtu信息
     */
    public void http() {

        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", "1512110003000001");

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_NODE_INFO, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null && response.getState() == 200) {
                    }
                }
            }, map);
        }
    }


    @Override
    public Class<?> getClazz() {
        return IndexSensorInfoFragment.class;
    }
}
