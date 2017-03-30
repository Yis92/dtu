package com.sixe.dtu.vm.index;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexDtuInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.index.child.UpdateDtuInfoActivity;
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

    private Button btnUpdate;//修改dtu信息：管理员和高级员工操作

    private String dtu_sn;//dtu编号

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
        btnUpdate = findView(R.id.btn_update);
    }

    @Override
    public void initData(Bundle bundle) {

        dtu_sn = bundle.getString(Constant.DTU_SN);

        //普通用户不可以修改dtu信息
        int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, 12);
        if (user_level != 12) {
            btnUpdate.setVisibility(View.VISIBLE);
        }
        queryDtuInfo();
    }

    @Override
    public void initEvents() {
        //修改dtu信息
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                startActivity(UpdateDtuInfoActivity.class, bundle);
            }
        });
    }

    /**
     * 查询dtu信息
     */
    public void queryDtuInfo() {

        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
//            map.put("dtu_sn", "1512110003000001");
            map.put("dtu_sn", dtu_sn);

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

                        // dtu_comm_type  通信方式 0：gprs，1：wifi
                        String type = response.getResult().getDtu_comm_type();
                        if (type.equals("0")) {
                            dtu_comm_type.setText("GRPS");
                        } else {
                            dtu_comm_type.setText("WIFI");
                        }

                        dtu_sim_no.setText(response.getResult().getDtu_sim_no());
                        dtu_warning_type.setText(response.getResult().getDtu_warning_type());
                        dtu_upfreq.setText(response.getResult().getDtu_upfreq());
                    }
                }
            }, map);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 200) {
            queryDtuInfo();
        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexDtuInfoFragment.class;
    }
}
