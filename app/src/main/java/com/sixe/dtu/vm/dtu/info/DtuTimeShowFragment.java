package com.sixe.dtu.vm.dtu.info;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.dtu.DtuTimeShowResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.dtu.DtuTimeShowListAdapter;
import com.sixe.dtu.widget.SuperRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * dtu实时数据展示
 * Created by liu on 17/3/2.
 */

public class DtuTimeShowFragment extends BaseFragment {

    private TextView tvTime;
    private SuperRefreshLayout mRefreshLayout;
    private ListView listView;
    private DtuTimeShowListAdapter adapter;
    private List<List<String>> dataList;

    private String dtu_sh;//dtu编号

    private HttpLoadingDialog httpLoadingDialog;

    private boolean isRefresh;//是否是下拉刷新操作

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_dtu_time_show, viewGroup, false);
    }

    @Override
    public void initBoot() {
        tvTime = findView(R.id.tv_time);
        mRefreshLayout = findView(R.id.superRefreshLayout);
        listView = findView(R.id.listView);
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        //监听上下拉加载
        mRefreshLayout.setSuperRefreshLayoutListener(new SuperRefreshLayout.SuperRefreshLayoutListener() {
            @Override
            public void onRefreshing() {
                //下拉刷新
                isRefresh = true;
                queryTimeData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多
            }
        });
    }

    @Override
    public void initData(Bundle bundle) {
        dtu_sh = bundle.getString(Constant.DTU_SN);

        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

//        mRefreshLayout.setCanLoadMore(listView);//是否可以加载更多

        queryTimeData();
    }

    @Override
    public void initEvents() {

    }

    /**
     * 查询实时数据信息
     */
    public void queryTimeData() {
        if (hasNetWork()) {

            if (isNotEmpty(dtu_sh)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("dtu_sn", dtu_sh);

                if (!isRefresh) {
                    httpLoadingDialog.visible();
                }

                HttpManager.postAsyn(HttpConstant.QUERRY_DTU_REALDATA, new HttpManager.ResultCallback<DtuTimeShowResp>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        httpLoadingDialog.dismiss();
                        mRefreshLayout.onLoadComplete();
                    }

                    @Override
                    public void onResponse(DtuTimeShowResp response) {
                        if (response != null && response.getState() == 200) {

                            tvTime.setText("观测时间:" + response.getDt());

                            if (!isRefresh) {
                                dataList = response.getResult();
                                adapter = new DtuTimeShowListAdapter(activity, dataList);
                                listView.setAdapter(adapter);
                            } else {
                                dataList = response.getResult();
                                adapter = new DtuTimeShowListAdapter(activity, dataList);
                                listView.setAdapter(adapter);
                                Snackbar snackbar = Snackbar.make(view, "已经是最新数据了哦~~~", Snackbar.LENGTH_SHORT);

                                snackbar.getView().setBackgroundResource(R.color.swiperefresh_color3);
                                snackbar.show();
                            }
                        }
                        mRefreshLayout.onLoadComplete();
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }

    @Override
    public Class<?> getClazz() {
        return DtuTimeShowFragment.class;
    }
}
