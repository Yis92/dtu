package com.sixe.dtu.vm.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.http.entity.user.UserCompanyInfoResp;
import com.sixe.dtu.vm.adapter.user.UserCompanyInfoListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;

/**
 * Created by liu on 17/2/25.
 */

public class UserFragment extends BaseFragment {

    private Toolbar tbTitle;

    private ListView lvContent;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_user, viewGroup, false);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        tbTitle = findView(R.id.tb_title);
        lvContent = findView(R.id.lv_content);
    }

    @Override
    public void initData(Bundle bundle) {
        tbTitle.inflateMenu(R.menu.userinfo);

        List<UserCompanyInfoResp.UserCompanyInfo> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            UserCompanyInfoResp.UserCompanyInfo info = new UserCompanyInfoResp().new UserCompanyInfo();
            info.setName("name");
            info.setValue("value");
            list.add(info);
        }
        UserCompanyInfoListAdapter adapter = new UserCompanyInfoListAdapter(activity, list);
        lvContent.setAdapter(adapter);
    }

    @Override
    public void initEvents() {
        tbTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.update_data:
                        ToastUtils.show(activity, "修改资料");
                        break;
                    case R.id.update_password:
                        ToastUtils.show(activity, "修改密码");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public Class<?> getClazz() {
        return UserFragment.class;
    }
}
