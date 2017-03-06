package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserCompanyInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;

/**
 * 修改单位信息
 * Created by liu on 17/3/4.
 */

public class UserUpdateCompanyInfoActivity extends BaseActivity {

    private Toolbar toolbar;

    private EditText etName;//公司名称
    private EditText etJingdu;//公司地理经度
    private EditText etWeidu;//公司地理纬度
    private EditText etWeizhi;//公司地理位置
    private EditText etDianhua1;//公司电话1
    private EditText etDianhua2;//公司电话2
    private EditText etDianhua3;//公司电话3
    private EditText etDianhua4;//公司电话4
    private EditText etDianhua5;//公司电话5
    private EditText etDianhua6;//公司电话6

    private Button btnSubmit;

    private String uint_no;//公司编号

    private HttpLoadingDialog httpLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_update_company_info);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);

        etName = findView(R.id.et_name);
        etJingdu = findView(R.id.et_jingdu);
        etWeidu = findView(R.id.et_weidu);
        etWeizhi = findView(R.id.et_weizhi);
        etDianhua1 = findView(R.id.et_dianhua1);
        etDianhua2 = findView(R.id.et_dianhua2);
        etDianhua3 = findView(R.id.et_dianhua3);
        etDianhua4 = findView(R.id.et_dianhua4);
        etDianhua5 = findView(R.id.et_dianhua5);
        etDianhua6 = findView(R.id.et_dianhua6);
        btnSubmit = findView(R.id.btn_submit);
    }

    @Override
    public void initData(Intent intent) {
        toolbar.setNavigationIcon(R.mipmap.white_back);

        uint_no = intent.getStringExtra(Constant.UNIT_NO);

        //查询单位信息
        queryCompanyInfo(uint_no);
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

        //修改
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEditNull = isEditNull();
                if (isEditNull) {
                    showToast("请确认是否有值未填写!");
                } else {
                    update();
                }
            }
        });
    }


    /**
     * 查询单位信息
     *
     * @param unit_no
     */
    public void queryCompanyInfo(String unit_no) {

        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("unit_no", unit_no);

            httpLoadingDialog.visible("修改中...");

            HttpManager.postAsyn(HttpConstant.QUERY_COMPANY, new HttpManager.ResultCallback<UserCompanyInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(UserCompanyInfoResp response) {
                    if (response != null && response.getState() == 200) {
                        UserCompanyInfoResp companyInfo = response.getResult();
                        etName.setText(companyInfo.getUnit_name());
                        etJingdu.setText(companyInfo.getUnit_long());
                        etWeidu.setText(companyInfo.getUnit_lat());
                        etWeizhi.setText(companyInfo.getAddress());
                        etDianhua1.setText(companyInfo.getUnit_tel1());
                        etDianhua2.setText(companyInfo.getUnit_tel2());
                        etDianhua3.setText(companyInfo.getUnit_tel3());
                        etDianhua4.setText(companyInfo.getUnit_tel4());
                        etDianhua5.setText(companyInfo.getUnit_tel5());
                        etDianhua6.setText(companyInfo.getUnit_tel6());
                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);

        }
    }

    /**
     * 修改单位信息
     */
    public void update() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("unit_name", getText(etName));
            map.put("unit_address", getText(etWeizhi));
            map.put("unit_long", getText(etJingdu));
            map.put("unit_lat", getText(etWeidu));
            map.put("unit_tel1", getText(etDianhua1));
            map.put("unit_tel2", getText(etDianhua2));
            map.put("unit_tel3", getText(etDianhua3));
            map.put("unit_tel4", getText(etDianhua4));
            map.put("unit_tel5", getText(etDianhua5));
            map.put("unit_tel6", getText(etDianhua6));

            httpLoadingDialog.visible();

            HttpManager.postAsyn(HttpConstant.UPDATE_COMPANY, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(CommonResponse response) {
                    if (response.getState() == 200) {
                        showToast("修改成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);

        }
    }

    /**
     * 是否为空 true 表示有空值
     */
    public boolean isEditNull() {
        if (isEmpty(etName)) {
            return true;
        }

        if (isEmpty(etWeizhi)) {
            return true;
        }

        if (isEmpty(etJingdu)) {
            return true;
        }

        if (isEmpty(etWeidu)) {
            return true;
        }

        if (isEmpty(etDianhua1)) {
            return true;
        }

        if (isEmpty(etDianhua2)) {
            return true;
        }
        if (isEmpty(etDianhua3)) {
            return true;
        }
        if (isEmpty(etDianhua4)) {
            return true;
        }
        if (isEmpty(etDianhua5)) {
            return true;
        }

        if (isEmpty(etDianhua6)) {
            return true;
        }
        return false;
    }

    @Override
    public Class<?> getClazz() {
        return UserUpdateCompanyInfoActivity.class;
    }
}
