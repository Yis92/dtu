package com.sixe.dtu.vm.dtu.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
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
 * dtu状态
 * Created by liu on 17/3/3.
 */

public class DtuStatusActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTime;
    private SuperRefreshLayout mRefreshLayout;
    private ListView listView;
    private DtuStatusListAdapter adapter;
    private List<List<String>> dataList;

    private String dtu_sn;//dtu编号

    private HttpLoadingDialog httpLoadingDialog;

    private boolean isRefresh;//是否是下拉刷新操作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_dtu_status);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        tvTime = findView(R.id.tv_time);
        mRefreshLayout = findView(R.id.superRefreshLayout);
        listView = findView(R.id.listView);
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initData(Intent intent) {

        toolbar.setNavigationIcon(R.mipmap.white_back);

        dtu_sn = intent.getExtras().getString(Constant.DTU_SN);

        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

//        mRefreshLayout.setCanLoadMore(listView);//是否可以加载更多


        queryDtuStatus();
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
                            Snackbar snackbar = Snackbar.make(tvTime, "已经是最新数据了哦~~~", Snackbar.LENGTH_SHORT);

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

    @Override
    public Class<?> getClazz() {
        return DtuStatusActivity.class;
    }
}
