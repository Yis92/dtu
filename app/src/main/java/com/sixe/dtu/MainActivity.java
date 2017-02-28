package com.sixe.dtu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.constant.Constant;
import com.sixe.dtu.http.entity.user.UserLoginResp;
import com.sixe.dtu.utils.RuntimeHelper;
import com.sixe.dtu.vm.index.IndexFragment;
import com.sixe.dtu.vm.test.UserResp;
import com.sixe.dtu.vm.user.UserFragment;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.base.CommonFragmentActivity;
import cn.trinea.android.common.util.ToastUtils;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    private List<Fragment> fragments;
    private Fragment fragment;
    private FragmentTransaction ft;
    private int currentTab; // 当前Tab页面索引
    private List<TextView> textViews;

    //菜单按钮
    private TextView tvIndex;
    private TextView tvUser;

    private long exitTime; //用于点击两次返回退出程序

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
    }

    @Override
    public void initBoot() {
    }

    @Override
    public void initViews() {
        tvIndex = findView(R.id.tv_index);
        tvUser = findView(R.id.tv_user);
    }

    @Override
    public void initData(Intent intent) {
        fragments = new ArrayList<>();
        IndexFragment indexFragment = new IndexFragment();
        Bundle bundle = new Bundle();
        UserLoginResp resp = (UserLoginResp) intent.getExtras().getSerializable(Constant.USER_INFO);

        bundle.putSerializable(Constant.USER_INFO, resp);
        indexFragment.setArguments(bundle);
        fragments.add(indexFragment);
        fragments.add(new UserFragment());

        textViews = new ArrayList<>();
        textViews.add(tvIndex);
        textViews.add(tvUser);

        show(tvIndex, 0);

    }

    @Override
    public void initEvents() {
        //切换底部菜单
        tvIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(tvIndex, 0);
            }
        });

        tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(tvUser, 1);
            }
        });
    }

    /**
     * 切换底部菜单栏
     *
     * @param textView 点击的tab
     * @param idx      当前位置
     */
    public void show(TextView textView, int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            fragment = fragments.get(i);
            ft = getSupportFragmentManager().beginTransaction();

            getCurrentFragment().onPause(); // 暂停当前tab
            if (idx == i) {
                if (fragment.isAdded()) {
                    fragment.onResume(); // 启动目标tab的onResume()
                } else {
                    ft.add(R.id.fl_content, fragment);
                }

                Drawable drawable = null;
                if (idx == 0) {
                    drawable = getResources().getDrawable(R.drawable.tab_index);
                } else {
                    drawable = getResources().getDrawable(R.drawable.tab_user);
                }
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, drawable, null, null);
                textView.setTextColor(getResources().getColor(R.color.color_45c717));
                textView.setSelected(true);
                ft.show(fragment);
            } else {
                textViews.get(i).setTextColor(getResources().getColor(R.color.color_666666));
                textViews.get(i).setSelected(false);
                ft.hide(fragment);
            }

            ft.commit();
            currentTab = idx; // 更新目标tab为当前tab
        }
    }

    /**
     * 获取当前Fragment
     *
     * @return
     */
    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show(this, "再按一次退出程序");
                RuntimeHelper.getInstance().setIsShowRedTask(false);
                exitTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Class<?> getClazz() {
        return MainActivity.class;
    }
}
