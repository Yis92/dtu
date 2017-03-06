package com.sixe.dtu.vm.index;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexMenu;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.vm.adapter.index.IndexMenuListAdapter;
import com.sixe.dtu.vm.user.UserLoginActivity;
import com.sixe.dtu.vm.user.UserPersonActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * Created by liu on 17/2/25.
 */

public class IndexFragment extends BaseFragment {

    private SimpleDraweeView sdvHead;//用户头像
    private TextView tvName;//用户名
    private ImageView ivMenu;//用于手动点击打开菜单
    private DrawerLayout drawerLayout;
    private ExpandableListView elvMenu;
    private TextView tvExit;//退出登录

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
        tvExit = findView(R.id.tv_exit);
    }

    @Override
    public void initData(Bundle bundle) {
        UserLoginResp userLoginResp = (UserLoginResp) bundle.getSerializable(Constant.USER_INFO);
        sdvHead.setImageURI(Uri.parse("http://img9.3lian.com/c1/vec2015/34/13.jpg"));
        httpMenu(userLoginResp);
    }

    @Override
    public void initEvents() {
        //打开菜单
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        //退出登录
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserLoginActivity.class);
                activity.finish();
            }
        });
        //我的信息
        sdvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserPersonActivity.class);
            }
        });
    }

    /**
     * 初始化菜单栏数据
     *
     * @param userLoginResp
     */
    public void httpMenu(UserLoginResp userLoginResp) {

        if (userLoginResp != null) {

            if (userLoginResp.getState() == 200) {

                //用户等级-  10：公司管理员，11：高级用户，12：普通用户
                int user_level = userLoginResp.getResult().getUser_level();

                //用户名
                tvName.setText("欢迎" + userLoginResp.getResult().getUser_id());

                //单位信息
                Map<String, IndexMenu> dataset = new HashMap<>();

                //获取接口返回的单位信息
                List<UserLoginResp.Company> company = userLoginResp.getResult().getUnits();

                //父菜单集合
                String[] parentList = new String[company.size()];

                for (int i = 0; i < company.size(); i++) {
                    //父菜单名字
                    parentList[i] = company.get(i).getUnit_name();

                    //子菜单信息
                    IndexMenu chileMenu = new IndexMenu();

                    //dtu信息
                    List<UserLoginResp.Company.DtuName> dtuNames = company.get(i).getDtu();

                    List<String> childName = new ArrayList<>();//子菜单名字
                    List<String> childId = new ArrayList<>();//子菜单id

                    //单位编号
                    String unit_no = company.get(i).getUnit_no();

                    childName.add("单位信息维护");
                    childId.add(company.get(i).getUnit_no());

                    if (user_level == 10) {
                        childName.add("用户信息维护");
                        childId.add(unit_no);
                    }

                    childName.add("dtu维护");
                    childId.add(unit_no);

                    childName.add("全部dtu信息");
                    childId.add(unit_no);

                    //循环遍历出子菜单中dtu的名字与编号
                    for (int j = 0; j < dtuNames.size(); j++) {
                        childName.add(dtuNames.get(j).getDtu_name());
                        childId.add(dtuNames.get(j).getDtu_name());
                    }

                    chileMenu.setName(childName);//公司名称
                    chileMenu.setId(childId);//公司编号

                    dataset.put(parentList[i], chileMenu);
                }

                IndexMenuListAdapter adapter = new IndexMenuListAdapter(activity, dataset, parentList);
                elvMenu.setAdapter(adapter);

                //点击子菜单关闭DrawLayout
                adapter.setOnClickMenuItem(new IndexMenuListAdapter.OnClickMenuItem() {
                    @Override
                    public void onClickMenuItem() {
                        drawerLayout.closeDrawers();
                    }
                });
            }
        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexFragment.class;
    }
}
