package com.sixe.dtu.vm.index;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.dtu.DtuTimeShowResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.utils.ShareWeChatUtils;
import com.sixe.dtu.vm.adapter.dtu.DtuTimeShowListAdapter;
import com.sixe.dtu.vm.index.child.GroupShowActivity;
import com.sixe.dtu.vm.index.child.UpdateAlarmInfoActivity;
import com.sixe.dtu.widget.SuperRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 数据显示
 * Created by liu on 17/3/7.
 */

public class IndexDataShowFragment extends BaseFragment {

    private TextView tvTime;
    private TextView tvShare;
    private SuperRefreshLayout mRefreshLayout;
    private ListView listView;
    private Button btnGroup;//分组数据展示
    private DtuTimeShowListAdapter adapter;
    private List<List<String>> dataList;

    private String dtu_sn;//dtu编号
    private String dtu_name;//dtu名称
    private String unit_name;//公司名称

    private HttpLoadingDialog httpLoadingDialog;

    private boolean isRefresh;//是否是下拉刷新操作

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_data_show, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        tvTime = findView(R.id.tv_time);
        tvShare = findView(R.id.tv_share);
        mRefreshLayout = findView(R.id.superRefreshLayout);
        listView = findView(R.id.listView);
        btnGroup = findView(R.id.btn_group);
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initData(Bundle bundle) {
        dtu_sn = bundle.getString(Constant.DTU_SN);
        dtu_name = bundle.getString(Constant.DTU_NAME);
        unit_name = bundle.getString("unit_name");

        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

//        mRefreshLayout.setCanLoadMore(listView);//是否可以加载更多

        queryTimeData();
    }

    @Override
    public void initEvents() {
        //分享
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop();
            }
        });
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
        //分组数据展示
        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                startActivity(GroupShowActivity.class, bundle);
            }
        });
    }

    /**
     * 查询实时数据信息
     */
    public void queryTimeData() {
        if (hasNetWork()) {

            if (isNotEmpty(dtu_sn)) {

                HashMap<String, String> map = new HashMap<>();
                map.put("dtu_sn", dtu_sn);

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
                            }
                            //管理员才可以修改
                            int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, 12);
                            if (user_level != 12) {
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data_no", dataList.get(i).get(3));
                                        bundle.putString(Constant.DTU_SN, dtu_sn);
                                        startActivity(UpdateAlarmInfoActivity.class, bundle);
                                    }
                                });
                            }
                        }
                        mRefreshLayout.onLoadComplete();
                        httpLoadingDialog.dismiss();
                    }
                }, map);
            }
        }
    }

    /**
     * 分享
     */
    public void sharePop() {
        View view = activity.getLayoutInflater().inflate(R.layout.pop_share_my_money, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        //外部是否可以点击
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        LinearLayout llMain = (LinearLayout) view.findViewById(R.id.ll_main);
        TextView tvWeixin = (TextView) view.findViewById(R.id.tv_weixin);
        TextView tvPengyouquan = (TextView) view.findViewById(R.id.tv_pengyouquan);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        //取消pop
        llMain.getBackground().setAlpha(150);
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //取消pop
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //微信好友
        tvWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent分享
                final ShareWeChatUtils shareWeChatUtils = new ShareWeChatUtils(activity, null, unit_name + dtu_name + "\nhttp://139.129.239.172:8080/comSys/dtuShare/goDataPage?nodeId=" + dtu_sn, 0);

                shareWeChatUtils.shareWeChat("");
            }
        });

        //微信朋友圈
        tvPengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent分享
                final ShareWeChatUtils shareWeChatUtils = new ShareWeChatUtils(activity, new String[]{"http://asdasd.png"}, unit_name + dtu_name + "\nhttp://139.129.239.172:8080/comSys/dtuShare/goDataPage?nodeId=" + dtu_sn, 0);

                //intent分享
//                shareWeChatUtils.shareLocalImg();
                shareWeChatUtils.share();
            }
        });

        //显示PopupWindow
        popupWindow.showAtLocation(tvShare, Gravity.CENTER, 0, 0);
    }


    @Override
    public Class<?> getClazz() {
        return IndexDataShowFragment.class;
    }
}
