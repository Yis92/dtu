package com.sixe.dtu.vm.dtu.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.vm.adapter.dtu.DtuDataInfoTabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * dtu数据
 * Created by liu on 17/3/2.
 */

public class DtuDataInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_dtu_data_info);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        toolbar = findView(R.id.tool_bar);
        mTabLayout = findView(R.id.tab_layout);
        mViewPager = findView(R.id.view_pager);
    }

    @Override
    public void initData(Intent intent) {

        toolbar.setNavigationIcon(R.mipmap.white_back);

        List<Fragment> fragments = new ArrayList<>();

        String dtu_sh = intent.getExtras().getString(Constant.DTU_SN);

        DtuTimeShowFragment lineShowFragment = new DtuTimeShowFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DTU_SN, dtu_sh);
        lineShowFragment.setArguments(bundle);
        fragments.add(lineShowFragment);

        DtuGroupShowFragment groupShowFragment = new DtuGroupShowFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constant.DTU_SN, dtu_sh);
        groupShowFragment.setArguments(bundle2);
        fragments.add(groupShowFragment);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("实时数据");
        tabNames.add("分组显示");

        FragmentManager fragmentManager = getSupportFragmentManager();
        DtuDataInfoTabLayoutAdapter adapter = new DtuDataInfoTabLayoutAdapter(fragmentManager, fragments, tabNames);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
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
    }

    @Override
    public Class<?> getClazz() {
        return DtuDataInfoActivity.class;
    }
}
