package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexDtuInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserStaffSpinnerAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 修改dtu信息
 * Created by sunny on 2017/3/29.
 */

public class UpdateDtuInfoActivity extends BaseActivity {

    private Toolbar toolbar;

    private EditText dtu_name;
    private EditText dtu_describ;
    private EditText dtu_address;
    private EditText dtu_long;
    private EditText dtu_lat;
    private Spinner dtu_comm_type;
    private EditText sim_no;
    private Spinner dtu_warning_type;
    private EditText dtu_upfreq;
    private Button btnSubmit;

    private String dtu_sn;
    private String selectorType = "0";//选择的通信类型
    private String selectorWarn = "0";//选择的报警类型

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_update_dtu_info);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);

        dtu_name = findView(R.id.dtu_name);
        dtu_describ = findView(R.id.dtu_describ);
        dtu_address = findView(R.id.dtu_address);
        dtu_long = findView(R.id.dtu_long);
        dtu_lat = findView(R.id.dtu_lat);
        dtu_comm_type = findView(R.id.dtu_comm_type);
        sim_no = findView(R.id.sim_no);
        dtu_warning_type = findView(R.id.dtu_warning_type);
        dtu_upfreq = findView(R.id.dtu_upfreq);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        String[] list = {"GPRS", "WIFI"};
        UserStaffSpinnerAdapter adapter = new UserStaffSpinnerAdapter(activity, list);
        dtu_comm_type.setAdapter(adapter);

        String[] list2 = {"app", "短信"};
        UserStaffSpinnerAdapter adapter2 = new UserStaffSpinnerAdapter(activity, list2);
        dtu_warning_type.setAdapter(adapter2);

        dtu_sn = intent.getExtras().getString(Constant.DTU_SN);

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
        //监听通信类型设置结果
        dtu_comm_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectorType = "0";
                } else {
                    selectorType = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //监听报警类型设置结果
        dtu_warning_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectorWarn = "0";
                } else {
                    selectorWarn = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //提交
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDtuInfo();
            }
        });
    }

    /**
     * 回现dtu信息
     */
    public void queryDtuInfo() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
//            map.put("dtu_sn", "1512110003000001");
            map.put("dtu_sn", dtu_sn);

            httpLoadingDialog.visible();

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_INFO, new HttpManager.ResultCallback<IndexDtuInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
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
                            dtu_comm_type.setSelection(0);
                        } else {
                            dtu_comm_type.setSelection(1);
                        }

                        //报警类型 0：app 1：短信warnType
                        String warnType = response.getResult().getDtu_warning_type();
                        if (warnType.equals("0")) {
                            dtu_warning_type.setSelection(0);
                        } else {
                            dtu_warning_type.setSelection(1);
                        }

                        sim_no.setText(response.getResult().getDtu_sim_no());
                        dtu_upfreq.setText(response.getResult().getDtu_upfreq());
                    }

                    httpLoadingDialog.dismiss();
                }
            }, map);
        }
    }

    /**
     * 修改dtu信息
     */
    public void updateDtuInfo() {
        if (hasNetWork()) {

            if (isEmpty(dtu_name)) {
                showToast("请输入dtu设备名称");
                return;
            }

            if (isEmpty(dtu_describ)) {
                showToast("请输入设备描述");
                return;
            }

            if (isEmpty(dtu_address)) {
                showToast("请输入安装位置");
                return;
            }
            if (isEmpty(dtu_long)) {
                showToast("请输入安装经度");
                return;
            }
            if (isEmpty(dtu_lat)) {
                showToast("请输入安装纬度");
                return;
            }
            if (isEmpty(sim_no)) {
                showToast("请输入sim卡号");
                return;
            }
            if (isEmpty(dtu_warning_type)) {
                showToast("请输入报警类型");
                return;
            }
            if (isEmpty(dtu_upfreq)) {
                showToast("请输入上传频率");
                return;
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);
            map.put("dtu_address", getText(dtu_address));
            map.put("dtu_type", "3");
            map.put("dtu_name", getText(dtu_name));
            map.put("dtu_describ", getText(dtu_describ));
            map.put("dtu_long", getText(dtu_long));
            map.put("dtu_lat", getText(dtu_lat));
            map.put("dtu_comm_type", selectorType);
            map.put("dtu_upfreq", getText(dtu_upfreq));
            map.put("dtu_sim_no", getText(sim_no));
            map.put("dtu_warning_type", selectorWarn);

            HttpManager.postAsyn(HttpConstant.UPDATE_DTU_INFO, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null && response.getState() == 200) {
                        showToast("修改成功");
                        setResult(200);
                        finish();
                    }
                }
            }, map);
        }
    }


    @Override
    public Class<?> getClazz() {
        return UpdateDtuInfoActivity.class;
    }
}
