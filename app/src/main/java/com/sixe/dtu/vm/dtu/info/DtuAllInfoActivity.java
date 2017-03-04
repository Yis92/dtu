package com.sixe.dtu.vm.dtu.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.dtu.DtuAllInfoResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.dtu.DtuAllInfoListAdapter;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 查看改公司所有dtu信息
 * Created by liu on 17/3/2.
 */

public class DtuAllInfoActivity extends BaseActivity {

    private Toolbar toolbar;

    private ListView lvContent;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_dtu_all_info);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        lvContent = findView(R.id.lv_content);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        tvTitle.setText(intent.getStringExtra(Constant.UNIT_NAME) + "dtu详情");
        String unit_no = intent.getStringExtra(Constant.UNIT_NO);//单位编号

        queryDtuInfo(unit_no);
    }

    @Override
    public void initEvents() {
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 查询该公司dtu信息
     */
    public void queryDtuInfo(String unit_no) {

        if (isNotEmpty(unit_no)) {

            if (hasNetWork()) {

                HashMap<String, String> map = new HashMap<>();
                map.put("unit_no", unit_no);

                httpLoadingDialog.visible();

                HttpManager.postAsyn(HttpConstant.QUERRY_UNIT_INFO2, new HttpManager.ResultCallback<DtuAllInfoResp>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(DtuAllInfoResp response) {
                        if (response != null && response.getState() == 200) {
                            DtuAllInfoListAdapter adapter = new DtuAllInfoListAdapter(activity, response.getResult());
                            lvContent.setAdapter(adapter);
                        }
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }

    @Override
    public Class<?> getClazz() {
        return DtuAllInfoActivity.class;
    }
}
