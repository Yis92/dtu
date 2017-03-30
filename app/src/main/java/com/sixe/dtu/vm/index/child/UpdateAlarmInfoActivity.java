package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexAlarmInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserStaffSpinnerAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 设置报警信息
 * Created by Administrator on 2017/3/30.
 */

public class UpdateAlarmInfoActivity extends BaseActivity {

    private Toolbar toolbar;

    private EditText up;//报警上限
    private EditText low;//报警下限
    private EditText lasting;//持续时间
    private EditText interval;//报警间隔
    private Spinner enable;//是否报警
    private String isSelector;

    private Button btnSubmit;//提交

    private String data_no;
    private String dtu_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_update_alarm_info);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        up = findView(R.id.up);
        low = findView(R.id.low);
        lasting = findView(R.id.lasting);
        interval = findView(R.id.interval);
        enable = findView(R.id.enable);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {

        toolbar.setNavigationIcon(R.mipmap.white_back);

        Bundle bundle = intent.getExtras();
        dtu_sn = bundle.getString(Constant.DTU_SN);
        IndexAlarmInfoResp resp = (IndexAlarmInfoResp) bundle.getSerializable(Constant.ALARM_INFO);

        data_no = resp.getData_no();

        up.setText(resp.getUp());
        low.setText(resp.getLow());
        lasting.setText(resp.getLasting());
        interval.setText(resp.getInterval());

        String[] list = {"否", "是"};
        UserStaffSpinnerAdapter adapter = new UserStaffSpinnerAdapter(activity, list);
        enable.setAdapter(adapter);
        if (resp.getEnable().equals("0")) {
            isSelector = "0";
            enable.setSelection(0);
        } else {
            isSelector = "1";
            enable.setSelection(1);
        }
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
        //监听是否报警设置结果
        enable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isSelector = "0";
                } else {
                    isSelector = "1";
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
                updateAlarmInfo();
            }
        });
    }

    /**
     * 修改报警信息
     */
    public void updateAlarmInfo() {
        if (hasNetWork()) {
            //表单空验证

            if (isEmpty(up)) {
                showToast("请输入报警上限");
                return;
            }

            if (isEmpty(low)) {
                showToast("请输入报警下限");
                return;
            }

            if (isEmpty(lasting)) {
                showToast("请输入持续时间");
                return;
            }

            if (isEmpty(interval)) {
                showToast("请输入报警间隔");
                return;
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);
            map.put("warn_up", getText(up));
            map.put("warn_low", getText(low));
            map.put("warn_lasting", getText(lasting));
            map.put("warn_interval", getText(interval));
            map.put("data_no", data_no);
            map.put("warn_enable", isSelector);

            HttpManager.postAsyn(HttpConstant.UPDATE_DTU_SENSOR_WARNING_INFO, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null && response.getState() == 200) {
                        showToast("设置成功");
                        setResult(200);
                        finish();
                    }
                }
            }, map);


        } else {
            showNetWorkError();
        }
    }

    @Override
    public Class<?> getClazz() {
        return UpdateAlarmInfoActivity.class;
    }
}
