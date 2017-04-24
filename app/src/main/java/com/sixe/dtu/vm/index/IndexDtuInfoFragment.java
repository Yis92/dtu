package com.sixe.dtu.vm.index;

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
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.dtu.DtuStatusListAdapter;
import com.sixe.dtu.widget.SuperRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * dtu信息 详情 - 改为dtu状态
 * Created by liu on 17/3/6.
 */

public class IndexDtuInfoFragment extends BaseFragment {

    private TextView tvTime;
    private SuperRefreshLayout mRefreshLayout;
    private ListView listView;
    private DtuStatusListAdapter adapter;
    private List<List<String>> dataList;

    private String dtu_sn;//dtu编号

    private HttpLoadingDialog httpLoadingDialog;

    private boolean isRefresh;//是否是下拉刷新操作

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_dtu_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        tvTime = findView(R.id.tv_time);
        mRefreshLayout = findView(R.id.superRefreshLayout);
        listView = findView(R.id.listView);
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initData(Bundle bundle) {

        dtu_sn = bundle.getString(Constant.DTU_SN);

        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

//        mRefreshLayout.setCanLoadMore(listView);//是否可以加载更多


        queryDtuStatus();
    }

    @Override
    public void initEvents() {
        //监听上下拉加载
        mRefreshLayout.setSuperRefreshLayoutListener(new SuperRefreshLayout.SuperRefreshLayoutListener() {
            @Override
            public void onRefreshing() {
                //下拉刷新
                isRefresh = true;
                queryDtuStatus();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多

            }
        });
    }
    /**
     * dtu状态
     */
    public void queryDtuStatus() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
//            map.put("dtu_sn", "1703030003000001");
            map.put("dtu_sn", dtu_sn);

            if (!isRefresh) {
                httpLoadingDialog.visible();
            }

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_STATE, new HttpManager.ResultCallback<DtuTimeShowResp>() {
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
                            adapter = new DtuStatusListAdapter(activity, response.getResult());
                            listView.setAdapter(adapter);
                        } else {
                            dataList = response.getResult();
                            adapter.notifyDataSetChanged();
//                            Snackbar snackbar = Snackbar.make(tvTime, "已经是最新数据了哦~~~", Snackbar.LENGTH_SHORT);
//
//                            snackbar.getView().setBackgroundResource(R.color.swiperefresh_color3);
//                            snackbar.show();
                        }
                    }
                    mRefreshLayout.onLoadComplete();
                    httpLoadingDialog.dismiss();
                }
            }, map);
        }

    }

    @Override
    public Class<?> getClazz() {
        return IndexDtuInfoFragment.class;
    }
}
