package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserStaffSpinnerAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 设置控制器任务信息
 * Created by Administrator on 2017/4/17.
 */

public class UpdateControlTaskActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTitle;

    private String dtu_sn;
    private String node_addr;
    private String tskCurrentChannel;
    private String tskType;
    private String tskDt;
    private String tskTm;
    private String tskSecond;

    private EditText tsk_current_channel;
    private Spinner tsk_type;
    private EditText tsk_dt;
    private EditText tsk_tm;
    private EditText tsk_second;
    private Button btnSubmit;

    private String isSelectorTskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_update_control_task);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        tvTitle = findView(R.id.tv_title);
        tsk_current_channel = findView(R.id.tsk_current_channel);
        tsk_type = findView(R.id.tsk_type);
        tsk_dt = findView(R.id.tsk_dt);
        tsk_tm = findView(R.id.tsk_tm);
        tsk_second = findView(R.id.tsk_second);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {

        tvTitle.setText("设置控制节点任务信息");
        toolbar.setNavigationIcon(R.mipmap.white_back);

        Bundle bundle = intent.getExtras();
        dtu_sn = bundle.getString("dtu_sn");
        node_addr = bundle.getString("node_addr");
        tskCurrentChannel = bundle.getString("tsk_current_channel");
        tskType = bundle.getString("tsk_type");
        tskDt = bundle.getString("tsk_dt");
        tskTm = bundle.getString("tsk_tm");
        tskSecond = bundle.getString("tsk_second");

        tsk_current_channel.setText(tskCurrentChannel);
        tsk_dt.setText(tskDt);
        tsk_tm.setText(tskTm);
        tsk_second.setText(tskSecond);

        String[] list = {"立即关闭", "立即打开", "计划打开", "没有任务"};
        UserStaffSpinnerAdapter adapter = new UserStaffSpinnerAdapter(activity, list);
        tsk_type.setAdapter(adapter);
        if (tskType.equals("0")) {
            isSelectorTskType = "0";
            tsk_type.setSelection(0);
        } else if (tskType.equals("1")) {
            isSelectorTskType = "1";
            tsk_type.setSelection(1);
        } else if (tskType.equals("2")) {
            isSelectorTskType = "2";
            tsk_type.setSelection(2);
        } else if (tskType.equals("3")) {
            isSelectorTskType = "3";
            tsk_type.setSelection(3);
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
        tsk_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isSelectorTskType = "0";
                } else if (position == 1) {
                    isSelectorTskType = "1";
                } else if (position == 2) {
                    isSelectorTskType = "2";
                } else if (position == 3) {
                    isSelectorTskType = "3";
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
                if (hasNetWork()) {
                    if (isEmpty(tsk_current_channel)) {
                        showToast("请填写当前通道");
                        return;
                    }

                    if (isEmpty(tsk_dt)) {
                        showToast("请填写任务执行日期");
                        return;
                    }

                    if (isEmpty(tsk_tm)) {
                        showToast("请填写任务执行时间");
                        return;
                    }

                    if (isEmpty(tsk_second)) {
                        showToast("请填写任务执行周期");
                        return;
                    }

                    String user_id = getPreferenceHelper().getString(Constant.USER_ID, "");

                    HashMap<String, String> map = new HashMap<>();
                    map.put("dtu_sn", dtu_sn);
                    map.put("node_addr", node_addr);
                    map.put("tsk_num", "8");
                    map.put("tsk_current_channel", getText(tsk_current_channel));
                    map.put("tsk_type", isSelectorTskType);
                    map.put("tsk_dt", getText(tsk_dt));
                    map.put("tsk_tm", getText(tsk_tm));
                    map.put("tsk_second", getText(tsk_second));
                    map.put("op_user", user_id);

                    HttpManager.postAsyn(HttpConstant.UPDATE_DTU_CTRL_NODE_TASK, new HttpManager.ResultCallback<CommonResponse>() {
                        @Override
                        public void onError(Request request, Exception e) {

                        }

                        @Override
                        public void onResponse(CommonResponse response) {
                            if (response != null && response.getState() == 200) {
                                setResult(RESULT_OK);
                                finish();
                                showToast("修改成功");
                            }
                        }
                    }, map);
                }
            }
        });
    }

    @Override
    public Class<?> getClazz() {
        return UpdateControlTaskActivity.class;
    }
}
