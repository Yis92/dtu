package com.sixe.dtu.vm.adapter.index;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixe.dtu.R;
import com.sixe.dtu.vm.user.UserCompanyInfoActivity;

import java.util.List;
import java.util.Map;

/**
 * 菜单
 * Created by liu on 17/2/27.
 */

public class IndexMenuListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> dataset;
    private String[] parentList;

    public IndexMenuListAdapter(Context context, Map<String, List<String>> dataset, String[] parentList) {
        this.context = context;
        this.dataset = dataset;
        this.parentList = parentList;
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
        return dataset.get(parentList[parentPos]).get(childPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return dataset.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        return dataset.get(parentList[parentPos]).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return dataset.get(parentList[parentPos]);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.parent_item, null);
        }
        view.setTag(R.layout.parent_item, parentPos);
        view.setTag(R.layout.child_item, -1);
        TextView text = (TextView) view.findViewById(R.id.parent_title);
        ImageView ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        text.setText(parentList[parentPos]);

        if (b) {
            ivArrow.setBackgroundResource(R.mipmap.user_task_arrow_up);
        } else {
            ivArrow.setBackgroundResource(R.mipmap.user_task_arrow_down);
        }

        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_item, null);
        }
        view.setTag(R.layout.parent_item, parentPos);
        view.setTag(R.layout.child_item, childPos);
        final TextView text = (TextView) view.findViewById(R.id.child_title);
        ImageView ivMenu = (ImageView) view.findViewById(R.id.iv_menu);

        if (childPos == 0) {
            ivMenu.setBackgroundResource(R.mipmap.menu_user);
        } else if (childPos == 1) {
            ivMenu.setBackgroundResource(R.mipmap.menu_danwei);
        } else {
            ivMenu.setBackgroundResource(R.mipmap.menu_shebei);
        }

        LinearLayout llChildContent = (LinearLayout) view.findViewById(R.id.ll_ciled_content);
        text.setText(dataset.get(parentList[parentPos]).get(childPos));

        llChildContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String childName = text.getText().toString();
                switch (childName) {
                    case "单位信息维护":
                        Intent intent = new Intent(context, UserCompanyInfoActivity.class);
                        context.startActivity(intent);
                        break;
                    case "用户信息维护":
                        break;
                    case "dtu维护":
                        break;
                }

                //关闭DrawLayout
                onClickMenuItem.onClickMenuItem();
            }
        });
        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    private OnClickMenuItem onClickMenuItem;

    public void setOnClickMenuItem(OnClickMenuItem onClickMenuItem) {
        this.onClickMenuItem = onClickMenuItem;
    }

    public interface OnClickMenuItem {
        void onClickMenuItem();
    }
}
