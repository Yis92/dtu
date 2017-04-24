package com.sixe.dtu.vm.dtu.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexDtuInfoResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.index.child.UpdateDtuInfoActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * dtu信息
 * Created by Administrator on 2017/4/24.
 */

public class DtuInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tv_dtu_sn;//DTU序列号
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_dtu_info);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        tvTitle = findView(R.id.tv_title);
        tv_dtu_sn = findView(R.id.tv_dtu_sn);
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
    public void initData(Intent intent) {

        tvTitle.setText("DTU信息");
        toolbar.setNavigationIcon(R.mipmap.white_back);

        dtu_sn = intent.getExtras().getString(Constant.DTU_SN);

        //普通用户不可以修改dtu信息
        int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, 12);
        if (user_level != 12) {
            btnUpdate.setVisibility(View.VISIBLE);
        }
        queryDtuInfo();
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
        //修改dtu信息
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                startActivity(UpdateDtuInfoActivity.class, bundle, 100);
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
                        tv_dtu_sn.setText(dtu_sn);
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

                        //报警类型 0：app 1：短信warnType
                        String warnType = response.getResult().getDtu_warning_type();
                        if (warnType.equals("0")) {
                            dtu_warning_type.setText("app");
                        } else {
                            dtu_warning_type.setText("短信");
                        }

                        dtu_sim_no.setText(response.getResult().getDtu_sim_no());
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
        return DtuInfoActivity.class;
    }
}
