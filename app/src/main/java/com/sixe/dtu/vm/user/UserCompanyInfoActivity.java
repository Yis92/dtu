package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserCompanyInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.user.UserCompanyInfoListAdapter;
import com.sixe.dtu.widget.ScrollListView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 查看单位信息
 * Created by liu on 17/2/28.
 */

public class UserCompanyInfoActivity extends BaseActivity {

    private RelativeLayout rlBack;
    private TextView tvTitle;
    private ImageView ivSet;
    private RelativeLayout rlSet;

    private TextView tvBianhao;//公司编号
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
    private TextView tvDtuNum;//dtu数量

    private ScrollListView lvContent;
    private String uint_no;//公司编号

    private List<EditText> editTextList = new ArrayList<>();

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_company_info);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        rlBack = findView(R.id.rl_back);
        tvTitle = findView(R.id.tv_title);
        ivSet = findView(R.id.iv_set);
        rlSet = findView(R.id.rl_set);

        tvBianhao = findView(R.id.tv_bianhao);
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
        tvDtuNum = findView(R.id.tv_dtu_num);

        lvContent = findView(R.id.lv_content);
    }

    @Override
    public void initData(Intent intent) {

        //获取用户等级
        int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, -1);

        rlBack.setVisibility(View.VISIBLE);
        tvTitle.setText("单位信息");

        editTextList.add(etName);
        editTextList.add(etJingdu);
        editTextList.add(etWeidu);
        editTextList.add(etWeizhi);
        editTextList.add(etDianhua1);
        editTextList.add(etDianhua2);
        editTextList.add(etDianhua3);
        editTextList.add(etDianhua4);
        editTextList.add(etDianhua5);
        editTextList.add(etDianhua6);

        if (user_level == 10) {
            rlSet.setVisibility(View.VISIBLE);
            ivSet.setBackgroundResource(R.mipmap.update);
        } else {
            //不能编辑
            isNotEdit(editTextList);
        }

        uint_no = intent.getStringExtra(Constant.UNIT_NO);

        //查询单位信息
        queryCompanyInfo(uint_no);
    }

    @Override
    public void initEvents() {
        //返回
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //修改
        rlSet.setOnClickListener(new View.OnClickListener() {
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
                        tvBianhao.setText(companyInfo.getUnit_no());
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
                        tvDtuNum.setText(companyInfo.getDtu_num());

                        UserCompanyInfoListAdapter adapter = new UserCompanyInfoListAdapter(activity, response.getResult().getDtu());
                        lvContent.setAdapter(adapter);

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
                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);

        }
    }


    /**
     * 是否可以编辑
     *
     * @param editText
     */
    public void isNotEdit(List<EditText> editText) {
        for (int i = 0; i < editText.size(); i++) {
            editText.get(i).setFocusable(false);
            editText.get(i).setBackgroundResource(0);
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
        return UserCompanyInfoActivity.class;
    }
}
