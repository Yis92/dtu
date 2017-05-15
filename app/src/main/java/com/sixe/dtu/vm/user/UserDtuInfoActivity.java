package com.sixe.dtu.vm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.vm.adapter.dtu.DtuDataInfoTabLayoutAdapter;
import com.sixe.dtu.vm.dtu.info.DtuInfoActivity;
import com.sixe.dtu.vm.index.IndexAlarmInfoFragment;
import com.sixe.dtu.vm.index.IndexControlPointFragment;
import com.sixe.dtu.vm.index.IndexDataShowFragment;
import com.sixe.dtu.vm.index.IndexDtuInfoFragment;
import com.sixe.dtu.vm.index.IndexSensorInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * dtu信息
 * Created by sunny on 2017/4/14.
 */

public class UserDtuInfoActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivDtuInfo;

    //导航栏、dtu内容详情
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String dtu_sn;
    private String unit_name;
    private String dtu_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_dtu_info);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        ivBack = findView(R.id.iv_back);
        tvTitle = findView(R.id.tv_title);
        ivDtuInfo = findView(R.id.iv_dtu_info);

        mTabLayout = findView(R.id.tab_layout);
        mViewPager = findView(R.id.view_pager);
    }

    @Override
    public void initData(Intent intent) {
        Bundle bundle = intent.getExtras();
        dtu_name = bundle.getString(Constant.DTU_NAME);
        unit_name = bundle.getString("unit_name");
        if (bundle != null) {
            tvTitle.setText(dtu_name);

            dtu_sn = bundle.getString(Constant.DTU_SN);
            loadCompanyDtu();
        }
    }

    @Override
    public void initEvents() {
        //返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //dtu信息
        ivDtuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DTU_SN, dtu_sn);
                startActivity(DtuInfoActivity.class, bundle);
            }
        });
        //切换导航栏更新viewpager内容
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    /**
     * 加载公司dtu详情信息
     */
    public void loadCompanyDtu() {

        if (isNotEmpty(dtu_sn)) {

            //fragment信息
            List<Fragment> fragments = new ArrayList<>();

            //数据显示
            IndexDataShowFragment dataShowFragment = new IndexDataShowFragment();
            Bundle bundle6 = new Bundle();
            bundle6.putString(Constant.DTU_SN, dtu_sn);
            bundle6.putString(Constant.DTU_NAME, dtu_name);
            bundle6.putString("unit_name", unit_name);
            dataShowFragment.setArguments(bundle6);
            fragments.add(dataShowFragment);

            //dtu信息
            IndexDtuInfoFragment indexDtuInfoFragment = new IndexDtuInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.DTU_SN, dtu_sn);
            bundle.putString(Constant.DTU_NAME, dtu_name);
            bundle.putString("unit_name", unit_name);
            indexDtuInfoFragment.setArguments(bundle);
            fragments.add(indexDtuInfoFragment);

            //传感器节点信息
//            IndexSensorInfoFragment sensorInfoFragment = new IndexSensorInfoFragment();
//            Bundle bundle2 = new Bundle();
//            bundle2.putString(Constant.DTU_SN, dtu_sn);
//            sensorInfoFragment.setArguments(bundle2);
//            fragments.add(sensorInfoFragment);

            //控制节点信息
            IndexControlPointFragment controlPointFragment = new IndexControlPointFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString(Constant.DTU_SN, dtu_sn);
            bundle3.putString(Constant.DTU_NAME, dtu_name);
            bundle3.putString("unit_name", unit_name);
            controlPointFragment.setArguments(bundle3);
            fragments.add(controlPointFragment);

            //报警信息
            IndexAlarmInfoFragment alarmInfoFragment = new IndexAlarmInfoFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putString(Constant.DTU_SN, dtu_sn);
            bundle4.putString(Constant.DTU_NAME, dtu_name);
            bundle4.putString("unit_name", unit_name);
            alarmInfoFragment.setArguments(bundle4);
            fragments.add(alarmInfoFragment);

            List<String> tabNames = new ArrayList<>();
            tabNames.add("数据显示");
            tabNames.add("dtu状态");
//            tabNames.add("传感器节点信息");
            tabNames.add("控制节点信息");
            tabNames.add("报警信息");

            FragmentManager fragmentManager = getSupportFragmentManager();

            DtuDataInfoTabLayoutAdapter adapter = new DtuDataInfoTabLayoutAdapter(fragmentManager, fragments, tabNames);
            mViewPager.setAdapter(adapter);
            mViewPager.setOffscreenPageLimit(2);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public Class<?> getClazz() {
        return UserDtuInfoActivity.class;
    }
}
