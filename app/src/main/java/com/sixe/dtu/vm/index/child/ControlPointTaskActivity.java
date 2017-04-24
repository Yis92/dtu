package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.dtu.DtuGroupShowResp;
import com.sixe.dtu.http.entity.index.child.ControlGroupResp;
import com.sixe.dtu.http.entity.index.child.ControlPointTaskResp;
import com.sixe.dtu.http.util.CommonResponse;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.dtu.DtuGroupDataListAdapter;
import com.sixe.dtu.vm.adapter.dtu.DtuGroupShowListAdapter;
import com.sixe.dtu.vm.adapter.index.child.ControlGroupListAdapter;
import com.sixe.dtu.vm.adapter.index.child.ControlPointTaskListAdapter;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 控制节点任务状态
 * Created by Administrator on 2017/4/1.
 */

public class ControlPointTaskActivity extends BaseActivity {

    private Toolbar toolbar;
    private ListView lvGroup;//分组
    private ListView listView;
    private ControlPointTaskListAdapter adapter;
    private List<ControlGroupResp.TskinfoBean.TskBean> dataList;

    private String dtu_sn;
    private String nodeAddr;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_control_point_task);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        lvGroup = findView(R.id.lv_group);
        listView = findView(R.id.listView);
    }

    @Override
    public void initData(Intent intent) {

        dtu_sn = intent.getExtras().getString(Constant.DTU_SN);

        toolbar.setNavigationIcon(R.mipmap.white_back);

        queryGroupInfo();
//        queryControlPointTask();
    }

    @Override
    public void initEvents() {
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("dtu_sn", dtu_sn);
                bundle.putString("node_addr", nodeAddr);
                bundle.putString("tsk_current_channel", dataList.get(i).getTsk_channel());
                bundle.putString("tsk_type", dataList.get(i).getTsk_type());
                bundle.putString("tsk_dt", dataList.get(i).getTsk_dt());
                bundle.putString("tsk_tm", dataList.get(i).getTsk_tm());
                bundle.putString("tsk_second", dataList.get(i).getTsk_second());
                startActivity(UpdateControlTaskActivity.class,bundle,100);
            }
        });
    }

    /**
     * 查看分组数据
     */
    public void queryGroupInfo() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);
//            map.put("dtu_sn", "1512110003000001");

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_CTRL_TASK_GROUP_INFO, new HttpManager.ResultCallback<ControlGroupResp>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(ControlGroupResp response) {
                    if (response != null && response.getState() == 200) {

                        //分组
                        ControlGroupListAdapter dtuGroupDataListAdapter = new ControlGroupListAdapter(activity, response.getResult().getGroup());
                        lvGroup.setAdapter(dtuGroupDataListAdapter);

                        //
                        nodeAddr =  response.getResult().getGroup().get(0).getNode_addr();

                        dtuGroupDataListAdapter.setOnClickClassify(new ControlGroupListAdapter.OnClickClassify() {
                            @Override
                            public void clickClassify(String node_addr) {
                                nodeAddr = node_addr;
                                httpGroup(nodeAddr);
                            }
                        });

                        //分组数据
                        dataList = response.getResult().getTskinfo().getTsk();
                        adapter = new ControlPointTaskListAdapter(activity, dataList);
                        listView.setAdapter(adapter);
                    }
                }
            }, map);
        }
    }

    /**
     * 点击分组或者刷新按钮查看详情
     *
     * @param nodeAddr
     */
    public void httpGroup(String nodeAddr) {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();
            map.put("dtu_sn", dtu_sn);
//            map.put("dtu_sn", "1512110003000001");
            map.put("node_addr", "" + nodeAddr);

            httpLoadingDialog.visible();

            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_GROUPDATA, new HttpManager.ResultCallback<ControlGroupResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(ControlGroupResp response) {
                    if (response != null && response.getState() == 200) {

                        List<ControlGroupResp.TskinfoBean.TskBean> datas = response.getResult().getTskinfo().getTsk();

                        dataList.clear();
                        for (int i = 3; i < datas.size(); i++) {
                            dataList.add(datas.get(i));
                        }

                        adapter.notifyDataSetChanged();

                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==100 && resultCode==RESULT_OK) {
            queryGroupInfo();
        }
    }

    //    /**
//     * 查询控制节点任务状态
//     */
//    public void queryControlPointTask() {
//        if (hasNetWork()) {
//
//            HashMap<String, String> map = new HashMap<>();
//            map.put("dtu_sn", dtu_sn);
//            map.put("node_addr", "3");
//
//            HttpManager.postAsyn(HttpConstant.QUERRY_DTU_CTRL_NODE_TASK, new HttpManager.ResultCallback<ControlGroupResp>() {
//                @Override
//                public void onError(Request request, Exception e) {
//
//                }
//
//                @Override
//                public void onResponse(ControlGroupResp response) {
//                    if (response != null && response.getState() == 200) {
//                        dataList = response.getResult();
//                        adapter = new ControlPointTaskListAdapter(activity, dataList);
//                        listView.setAdapter(adapter);
//                    }
//                }
//            }, map);
//        } else {
//            showNetWorkError();
//        }
//    }

    @Override
    public Class<?> getClazz() {
        return ControlPointTaskActivity.class;
    }
}
