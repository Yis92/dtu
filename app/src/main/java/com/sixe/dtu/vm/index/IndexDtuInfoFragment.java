package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.http.entity.index.IndexDtuInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * dtu信息 详情
 * Created by liu on 17/3/6.
 */

public class IndexDtuInfoFragment extends BaseFragment {

    private TextView dtu_name;//DTU设备名称
    private TextView dtu_describ;//设备描述
    private TextView dtu_address;//安装位置
    private TextView dtu_long;//安装经度
    private TextView dtu_lat;//安装纬度
    private TextView dtu_comm_type;//通信类型
    private TextView dtu_sim_no;//sim卡号
    private TextView dtu_warning_type;//报警类型
    private TextView dtu_upfreq;//上传频率

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_dtu_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        dtu_name = findView(R.id.dtu_name);
        dtu_describ = findView(R.id.dtu_describ);
        dtu_address = findView(R.id.dtu_address);
        dtu_long = findView(R.id.dtu_long);
        dtu_lat = findView(R.id.dtu_lat);
        dtu_comm_type = findView(R.id.dtu_comm_type);
        dtu_sim_no = findView(R.id.dtu_sim_no);
        dtu_warning_type = findView(R.id.dtu_warning_type);
        dtu_upfreq = findView(R.id.dtu_upfreq);
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

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_INFO, new HttpManager.ResultCallback<IndexDtuInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(IndexDtuInfoResp response) {
                    if (response != null && response.getState() == 200) {
                        dtu_name.setText(response.getResult().getDtu_name());
                        dtu_describ.setText(response.getResult().getDtu_describ());
                        dtu_address.setText(response.getResult().getDtu_address());
                        dtu_long.setText(response.getResult().getDtu_long());
                        dtu_lat.setText(response.getResult().getDtu_lat());
                        dtu_comm_type.setText(response.getResult().getDtu_comm_type());
                        dtu_sim_no.setText(response.getResult().getDtu_sim_no());
                        dtu_warning_type.setText(response.getResult().getDtu_warning_type());
                        dtu_upfreq.setText(response.getResult().getDtu_upfreq());
                    }
                }
            }, map);
        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexDtuInfoFragment.class;
    }
}
