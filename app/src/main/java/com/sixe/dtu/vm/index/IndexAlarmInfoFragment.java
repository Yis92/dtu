package com.sixe.dtu.vm.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.child.AlarmInfoResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.child.AlarmInfoListAdapter;
import com.sixe.dtu.vm.index.child.AlarmInfoActivity;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;

/**
 * 报警信息
 * Created by liu on 17/3/7.
 */

public class IndexAlarmInfoFragment extends BaseFragment {

    private Button btnSet;//报警信息
    private ListView listView;
    private AlarmInfoListAdapter adapter;
    private List<AlarmInfoResp> dataList;

    private String dtu_sn;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index_alarm_info, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        btnSet = findView(R.id.btn_set);
        listView = findView(R.id.listView);
    }

    @Override
    public void initData(Bundle bundle) {
        dtu_sn = bundle.getString(Constant.DTU_SN);

        //普通用户不可以修改dtu信息
        int user_level = getPreferenceHelper().getInt(Constant.USER_LEVEL, 12);
        if (user_level != 12) {
            btnSet.setVisibility(View.VISIBLE);
        }

        queryAlarmInfo();
    }

    @Override
    public void initEvents() {
        //报警信息
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                startActivity(AlarmInfoActivity.class, bundle);
            }
        });
    }

    /**
     * 查看报警信息
     */
    public void queryAlarmInfo() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
//            map.put("dtu_sn", "1512110003000001");
            map.put("dtu_sn", dtu_sn);
            map.put("warning_type", "0");

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_SENSOR_WARNING_MSG, new HttpManager.ResultCallback<AlarmInfoResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(AlarmInfoResp response) {
                    if (response != null) {
                        if (response.getState() == 200) {
                            dataList = response.getResult();
                            adapter = new AlarmInfoListAdapter(activity, dataList);
                            listView.setAdapter(adapter);

                            adapter.setOnClickOperationAlarm(new AlarmInfoListAdapter.OnClickOperationAlarm() {
                                @Override
                                public void onClickOperationAlarm(int position) {
                                    signAlarmInfo(dataList.get(position).getMsgid());
                                }
                            });
                        }

                        if (response.getState() == 202) {
                            showToastResult("暂时没有报警信息~~~");
                        }
                    }

                }
            }, map);
        } else {
            showNetWorkError();
        }
    }


    /**
     * 标记已经处理过报警信息
     */
    public void signAlarmInfo(String msgid) {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);
            map.put("msgid", msgid);

            HttpManager.postAsyn(HttpConstant.DEAL_DTU_SENSOR_WARNING_MSG, new HttpManager.ResultCallback<CommonResponse>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null && response.getState() == 200) {
                        queryAlarmInfo();
                        showToastResult("处理成功");
                    }
                }
            }, map);
        } else {
            showNetWorkError();
        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexAlarmInfoFragment.class;
    }
}
