package com.sixe.dtu.vm.index;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.MainActivity;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.http.util.HttpConstant;
import com.sixe.dtu.http.util.HttpManager;
import com.sixe.dtu.vm.adapter.index.IndexMenuListAdapter;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 17/2/25.
 */

public class IndexFragment extends BaseFragment {

    private SimpleDraweeView sdvHead;
    private TextView tvName;
    private ImageView ivMenu;
    private DrawerLayout drawerLayout;
    private ExpandableListView elvMenu;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index, viewGroup, false);
    }

    @Override
    public void initBoot() {
        httpLoadingDialog = new HttpLoadingDialog(activity);
    }

    @Override
    public void initViews() {
        sdvHead = findView(R.id.sdv_head);
        tvName = findView(R.id.tv_name);
        ivMenu = findView(R.id.iv_menu);
        drawerLayout = findView(R.id.drawer_layout);
        elvMenu = findView(R.id.elv_menu);

    }

    @Override
    public void initData(Bundle bundle) {

        sdvHead.setImageURI(Uri.parse("http://img9.3lian.com/c1/vec2015/34/13.jpg"));
        httpMenu();

    }


    @Override
    public void initEvents() {
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    public void httpMenu() {
        if (hasNetWork()) {

            HashMap<String, String> map = new HashMap<>();

            map.put("user_id", "dhmaster");
            map.put("pwd", "123456");

            httpLoadingDialog.visible("加载中...");

            HttpManager.postAsyn(HttpConstant.LOGIN, new HttpManager.ResultCallback<UserLoginResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    httpLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(UserLoginResp response) {
                    if (response != null) {
                        if (response.getState() == 0) {

                            //
                            tvName.setText("欢迎" + response.getResult().getUser_id());
                            
                            //单位信息
                            Map<String, List<String>> dataset = new HashMap<>();
                            List<UserLoginResp.Company> company = response.getResult().getUnits();
                            String[] parentList = new String[company.size()];
                            for (int i = 0; i < company.size(); i++) {
                                List<String> childrenList = new ArrayList<>();
                                parentList[i] = company.get(i).getUnit_name();
                                childrenList.add("单位信息维护");
                                childrenList.add("用户信息维护");
                                childrenList.add("dtu维护");
                                dataset.put(parentList[i], childrenList);
                            }
                            IndexMenuListAdapter adapter = new IndexMenuListAdapter(activity, dataset, parentList);
                            elvMenu.setAdapter(adapter);
                        }
                    }
                    httpLoadingDialog.dismiss();
                }
            }, map);


        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexFragment.class;
    }
}
