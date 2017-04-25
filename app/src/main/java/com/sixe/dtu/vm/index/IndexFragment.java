package com.sixe.dtu.vm.index;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;
import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseFragment;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.index.IndexMenu;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.vm.adapter.dtu.DtuDataInfoTabLayoutAdapter;
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

    private ProgressBar progressBar;
    private WebView webView;//展示公司信息
    private ImageView ivShare;//分享
    private SimpleDraweeView sdvHead;//用户头像
    private TextView tvName;//用户名
    private ImageView ivMenu;//用于手动点击打开菜单
    private DrawerLayout drawerLayout;
    private ExpandableListView elvMenu;
    private TextView tvExit;//退出登录

    //加载进度、公司详情、dtu详情、三选一展示
    private RelativeLayout rlLoad;
    private LinearLayout llCompanyDetail;
    private LinearLayout llDtuDetail;

    //导航栏、dtu内容详情
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String dtu_sn;//dtu编号

    private Handler handler = new Handler();

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_index, viewGroup, false);
    }

    @Override
    public void initBoot() {
    }

    @Override
    public void initViews() {
        progressBar = findView(R.id.progressBar);
        webView = findView(R.id.web_view);
        ivShare = findView(R.id.iv_share);
        sdvHead = findView(R.id.sdv_head);
        tvName = findView(R.id.tv_name);
        ivMenu = findView(R.id.iv_menu);
        drawerLayout = findView(R.id.drawer_layout);
        elvMenu = findView(R.id.elv_menu);
        tvExit = findView(R.id.tv_exit);

        rlLoad = findView(R.id.rl_load);
        llCompanyDetail = findView(R.id.ll_company_detail);
        llDtuDetail = findView(R.id.ll_dtu_detail);

        mTabLayout = findView(R.id.tab_layout);
        mViewPager = findView(R.id.view_pager);
    }

    @Override
    public void initData(Bundle bundle) {
        //获取登录后传递来的数据,用于菜单栏展示
        UserLoginResp userLoginResp = (UserLoginResp) bundle.getSerializable(Constant.USER_INFO);
        sdvHead.setImageURI(Uri.parse("http://img9.3lian.com/c1/vec2015/34/13.jpg"));

        //加载菜单栏
        loadMenu(userLoginResp);

        llCompanyDetail.setVisibility(View.VISIBLE);
        llDtuDetail.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        //
        progressBar.setMax(100);
        final WebSettings webset = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webset.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webset.setSupportZoom(true);
        // 设置出现缩放工具
        webset.setBuiltInZoomControls(true);
        //不显示缩小镜和放大镜按钮
        webset.setDisplayZoomControls(false);
        //扩大比例的缩放
        webset.setUseWideViewPort(true);
        //自适应屏幕
        webset.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webset.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient());
        // 设置WebChromeClient，以支持运行特殊的Javascript
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    rlLoad.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.loadUrl("http://load.huolan.net/help.html");
//        webView.loadUrl("http://139.129.239.172:7710/appindex/appindex.html");
    }

    @Override
    public void initEvents() {
        //
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
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
     * 初始化菜单栏数据
     *
     * @param userLoginResp
     */
    public void loadMenu(UserLoginResp userLoginResp) {

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
//                    List<UserLoginResp.Company.DtuName> dtuNames = company.get(i).getDtu();

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

//                    childName.add("dtu维护");
//                    childId.add(unit_no);

//                    childName.add("全部dtu信息");
//                    childId.add(unit_no);

                    //循环遍历出子菜单中dtu的名字与编号
//                    for (int j = 0; j < dtuNames.size(); j++) {
//                        childName.add(dtuNames.get(j).getDtu_name());
//                        childId.add(dtuNames.get(j).getDtu_sn());
//                    }

                    chileMenu.setName(childName);//公司名称
                    chileMenu.setId(childId);//公司编号

                    dataset.put(parentList[i], chileMenu);
                }

                IndexMenuListAdapter adapter = new IndexMenuListAdapter(activity, dataset, parentList);
                elvMenu.setAdapter(adapter);

                //点击子菜单关闭DrawLayout
                adapter.setOnClickMenuItem(new IndexMenuListAdapter.OnClickMenuItem() {
                    @Override
                    public void onClickMenuItem(boolean isDtu, String id) {
                        if (isDtu) {
                            Log.i("http", "id:::" + id);
                            dtu_sn = id;
                            loadCompanyDtu();
                        }
                        drawerLayout.closeDrawers();
                    }
                });
            }
        }
    }

    /**
     * 加载公司dtu详情信息
     */
    public void loadCompanyDtu() {

        if (isNotEmpty(dtu_sn)) {

            rlLoad.setVisibility(View.GONE);
            llCompanyDetail.setVisibility(View.GONE);
            llDtuDetail.setVisibility(View.VISIBLE);

            //fragment信息
            List<Fragment> fragments = new ArrayList<>();

            //数据显示
            IndexDataShowFragment dataShowFragment = new IndexDataShowFragment();
            Bundle bundle6 = new Bundle();
            bundle6.putString(Constant.DTU_SN, dtu_sn);
            dataShowFragment.setArguments(bundle6);
            fragments.add(dataShowFragment);

            //dtu信息 改为dtu状态
            IndexDtuInfoFragment indexDtuInfoFragment = new IndexDtuInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.DTU_SN, dtu_sn);
            indexDtuInfoFragment.setArguments(bundle);
            fragments.add(indexDtuInfoFragment);

            //传感器节点信息
            IndexSensorInfoFragment sensorInfoFragment = new IndexSensorInfoFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString(Constant.DTU_SN, dtu_sn);
            sensorInfoFragment.setArguments(bundle2);
            fragments.add(sensorInfoFragment);

            //控制节点信息
            IndexControlPointFragment controlPointFragment = new IndexControlPointFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString(Constant.DTU_SN, dtu_sn);
            controlPointFragment.setArguments(bundle3);
            fragments.add(controlPointFragment);

            //报警信息
            IndexAlarmInfoFragment alarmInfoFragment = new IndexAlarmInfoFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putString(Constant.DTU_SN, dtu_sn);
            alarmInfoFragment.setArguments(bundle4);
            fragments.add(alarmInfoFragment);

            //分组信息
//            IndexGroupInfoFragment groupInfoFragment = new IndexGroupInfoFragment();
//            Bundle bundle5 = new Bundle();
//            bundle5.putString(Constant.DTU_SN, dtu_sn);
//            groupInfoFragment.setArguments(bundle5);
//            fragments.add(groupInfoFragment);

            List<String> tabNames = new ArrayList<>();
            tabNames.add("数据显示");
            tabNames.add("dtu状态");
            tabNames.add("传感器节点信息");
            tabNames.add("控制节点信息");
            tabNames.add("报警信息");
//            tabNames.add("分组信息");


            FragmentManager fragmentManager = getFragmentManager();

            DtuDataInfoTabLayoutAdapter adapter = new DtuDataInfoTabLayoutAdapter(fragmentManager, fragments, tabNames);
            mViewPager.setAdapter(adapter);
            mViewPager.setOffscreenPageLimit(2);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public Class<?> getClazz() {
        return IndexFragment.class;
    }
}
