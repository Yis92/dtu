package com.sixe.dtu.vm.adapter.user;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.widget.ScrollGridView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;

/**
 * Created by Administrator on 2017/4/14.
 */

public class UserHomeListAdapter extends CommonAdapter<UserLoginResp.Company> {

    private TextView tvCompanyName;
    private ScrollGridView sgvDtu;

    public UserHomeListAdapter(Activity activity, List<UserLoginResp.Company> list) {
        super(activity, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_user_home_lv_item, viewGroup, false);
        }

        tvCompanyName = get(view, R.id.tv_company_name);
        sgvDtu = get(view, R.id.sgv_dtu);

        tvCompanyName.setText("  "+list.get(i).getUnit_name());
        UserHomeGridAdapter adapter = new UserHomeGridAdapter(activity, list.get(i).getDtu(),list.get(i).getUnit_name());
        sgvDtu.setAdapter(adapter);

        return view;
    }
}
