package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
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
    private TextView etName;//公司名称
    private TextView etJingdu;//公司地理经度
    private TextView etWeidu;//公司地理纬度
    private TextView etWeizhi;//公司地理位置
    private TextView etDianhua1;//公司电话1
    private TextView etDianhua2;//公司电话2
    private TextView etDianhua3;//公司电话3
    private TextView etDianhua4;//公司电话4
    private TextView etDianhua5;//公司电话5
    private TextView etDianhua6;//公司电话6
    private TextView tvDtuNum;//dtu数量

    private ScrollListView lvContent;
    private String uint_no;//公司编号

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

        if (user_level == 10) {
            rlSet.setVisibility(View.VISIBLE);
            ivSet.setBackgroundResource(R.mipmap.update);
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
                Intent intent = new Intent(activity, UserUpdateCompanyInfoActivity.class);
                intent.putExtra(Constant.UNIT_NO, uint_no);
                startActivityForResult(intent, 100);
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

            httpLoadingDialog.visible();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 & resultCode == RESULT_OK) {
            queryCompanyInfo(uint_no);
        }
    }

    @Override
    public Class<?> getClazz() {
        return UserCompanyInfoActivity.class;
    }
}
