package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.dtu.DtuGroupShowResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.dtu.DtuGroupDataListAdapter;
import com.sixe.dtu.vm.adapter.dtu.DtuGroupShowListAdapter;
import com.sixe.dtu.widget.ScrollGridView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 分组信息
 * Created by liu on 17/3/7.
 */

public class IndexGroupInfoFragment extends BaseFragment {
    private TextView tvTime;
    private Button btnUpdateData;
    private ListView lvGroup;//分组
    private ScrollGridView lvContent;

    private LinearLayout llOne;
    private TextView tvNameOne;
    private TextView tvValueOne;
    private LinearLayout llTwo;
    private TextView tvNameTwo;
    private TextView tvValueTwo;
    private LinearLayout llThree;
    private TextView tvNameThree;
    private TextView tvValueThree;

    private DtuGroupShowListAdapter adapter;
    private List<DtuGroupShowResp.GroupData> groupDatas;

    private HttpLoadingDialog httpLoadingDialog;

    private int groupId = 0;
    private String dtu_sh;//dtu编号

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_dtu_group_show, viewGroup, false);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        tvTime = findView(R.id.tv_time);
        btnUpdateData = findView(R.id.btn_update_data);
        lvGroup = findView(R.id.lv_group);
        lvContent = findView(R.id.lv_content);

        llOne = findView(R.id.ll_one);
        tvNameOne = findView(R.id.tv_one_name);
        tvValueOne = findView(R.id.tv_one_value);
        llTwo = findView(R.id.ll_two);
        tvNameTwo = findView(R.id.tv_two_name);
        tvValueTwo = findView(R.id.tv_two_value);
        llThree = findView(R.id.ll_three);
        tvNameThree = findView(R.id.tv_three_name);
        tvValueThree = findView(R.id.tv_three_value);
    }

    @Override
    public void initData(Bundle bundle) {
        dtu_sh = bundle.getString(Constant.DTU_SN);

        http();
    }

    @Override
    public void initEvents() {
        //更新数据
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupId != 0) {
                    httpGroup(groupId);
                }
            }
        });
    }


    /**
     * 点击分组或者刷新按钮查看详情
     *
     * @param group_id
     */
    public void httpGroup(int group_id) {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sh);
//            map.put("dtu_sn", "1512110003000001");
            map.put("group_id", "" + group_id);

            httpLoadingDialog.visible();

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_GROUPDATA, new HttpManager.ResultCallback<DtuGroupShowResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(DtuGroupShowResp response) {
                    if (response != null && response.getState() == 200) {

                        tvTime.setText("观测时间:" + response.getDt());

                        List<DtuGroupShowResp.GroupData> datas = response.getResult().getGroupdata();

                        if (datas.size() == 1) {
                            llOne.setVisibility(View.VISIBLE);
                            llTwo.setVisibility(View.GONE);
                            llThree.setVisibility(View.GONE);
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                        } else if (datas.size() == 2) {
                            llOne.setVisibility(View.VISIBLE);
                            llTwo.setVisibility(View.VISIBLE);
                            llThree.setVisibility(View.GONE);
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                            tvNameTwo.setText(datas.get(1).getName());
                            tvValueTwo.setText(datas.get(1).getValue());
                        } else if (datas.size() == 3) {
                            llOne.setVisibility(View.VISIBLE);
                            llTwo.setVisibility(View.VISIBLE);
                            llThree.setVisibility(View.VISIBLE);
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                            tvNameTwo.setText(datas.get(1).getName());
                            tvValueTwo.setText(datas.get(1).getValue());
                            tvNameThree.setText(datas.get(2).getName());
                            tvValueThree.setText(datas.get(2).getValue());
                        } else {
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                            tvNameTwo.setText(datas.get(1).getName());
                            tvValueTwo.setText(datas.get(1).getValue());
                            tvNameThree.setText(datas.get(2).getName());
                            tvValueThree.setText(datas.get(2).getValue());

                            groupDatas.clear();
                            for (int i = 3; i < datas.size(); i++) {
                                groupDatas.add(datas.get(i));
                            }

                            adapter.notifyDataSetChanged();
                        }

                        Snackbar snackbar = Snackbar.make(view, "已经是最新数据了哦~~~", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundResource(R.color.swiperefresh_color3);
                        snackbar.show();
                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);
        }
    }

    /**
     * 查看分组数据
     */
    public void http() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sh);
//            map.put("dtu_sn", "1512110003000001");

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_GROUPINFO, new HttpManager.ResultCallback<DtuGroupShowResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(DtuGroupShowResp response) {
                    if (response != null && response.getState() == 200) {

                        tvTime.setText("观测时间:" + response.getDt());

                        groupId = response.getResult().getGroup().get(0).getGroup_id();

                        //分组
                        DtuGroupDataListAdapter dtuGroupDataListAdapter = new DtuGroupDataListAdapter(activity, response.getResult().getGroup());
                        lvGroup.setAdapter(dtuGroupDataListAdapter);

                        dtuGroupDataListAdapter.setOnClickClassify(new DtuGroupDataListAdapter.OnClickClassify() {
                            @Override
                            public void clickClassify(int group_id) {
                                groupId = group_id;
                                httpGroup(group_id);
                            }
                        });

                        //分组数据
                        List<DtuGroupShowResp.GroupData> datas = response.getResult().getGroupdata();

                        groupDatas = new ArrayList<>();

                        if (datas.size() == 1) {
                            llOne.setVisibility(View.VISIBLE);
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                        } else if (datas.size() == 2) {
                            llOne.setVisibility(View.VISIBLE);
                            llTwo.setVisibility(View.VISIBLE);
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                            tvNameTwo.setText(datas.get(1).getName());
                            tvValueTwo.setText(datas.get(1).getValue());
                        } else if (datas.size() == 3) {
                            llOne.setVisibility(View.VISIBLE);
                            llTwo.setVisibility(View.VISIBLE);
                            llThree.setVisibility(View.VISIBLE);
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                            tvNameTwo.setText(datas.get(1).getName());
                            tvValueTwo.setText(datas.get(1).getValue());
                            tvNameThree.setText(datas.get(2).getName());
                            tvValueThree.setText(datas.get(2).getValue());
                        } else {
                            tvNameOne.setText(datas.get(0).getName());
                            tvValueOne.setText(datas.get(0).getValue());
                            tvNameTwo.setText(datas.get(1).getName());
                            tvValueTwo.setText(datas.get(1).getValue());
                            tvNameThree.setText(datas.get(2).getName());
                            tvValueThree.setText(datas.get(2).getValue());

                            for (int i = 3; i < datas.size(); i++) {
                                groupDatas.add(datas.get(i));
                            }

                            adapter = new DtuGroupShowListAdapter(activity, groupDatas);
                            lvContent.setAdapter(adapter);
                        }
                    }
                }
            }, map);
        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexGroupInfoFragment.class;
    }
}
