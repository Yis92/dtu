package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexDtuInfoResp;
import com.sixe.dtu.http.entity.index.IndexSensorInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.IndexSensorInfoListAdapter;
import com.sixe.dtu.widget.SuperRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 传感器节点信息
 * Created by liu on 17/3/7.
 */

public class IndexSensorInfoFragment extends BaseFragment {

    private SuperRefreshLayout mRefreshLayout;
    private ListView listView;

    private IndexSensorInfoListAdapter adapter;
    private List<IndexSensorInfoResp> dataList;

    private boolean isRefresh = false;//是否为下拉刷新
    private String dtu_sh;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_sensor_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        mRefreshLayout = findView(R.id.superRefreshLayout);
        listView = findView(R.id.listView);
    }

    @Override
    public void initData(Bundle bundle) {

        dtu_sh = bundle.getString(Constant.DTU_SN);

        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        querySensorInfo();

        //        mRefreshLayout.setCanLoadMore(listView);//是否可以加载更多
    }

    @Override
    public void initEvents() {
        //监听上下拉加载
        mRefreshLayout.setSuperRefreshLayoutListener(new SuperRefreshLayout.SuperRefreshLayoutListener() {
            @Override
            public void onRefreshing() {
                //下拉刷新
                isRefresh = true;
                querySensorInfo();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多
            }
        });
    }

    /**
     * 查询dtu信息
     */
    public void querySensorInfo() {

        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sh);

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_NODE_INFO, new HttpManager.ResultCallback<IndexSensorInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    mRefreshLayout.onLoadComplete();
                }

                @Override
                public void onResponse(IndexSensorInfoResp response) {
                    if (response != null && response.getState() == 200) {
                        if (!isRefresh) {
                            dataList = response.getResult();
                            adapter = new IndexSensorInfoListAdapter(activity, dataList);
                            listView.setAdapter(adapter);
                        } else {
                            dataList = response.getResult();
                            adapter.notifyDataSetInvalidated();
                            mRefreshLayout.onLoadComplete();
//                            showRefreshResult();
                        }

                    }

                }
            }, map);
        }
    }


    @Override
    public Class<?> getClazz() {
        return IndexSensorInfoFragment.class;
    }
}
