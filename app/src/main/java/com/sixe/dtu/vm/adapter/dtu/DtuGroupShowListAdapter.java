package com.sixe.dtu.vm.adapter.dtu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.http.entity.dtu.DtuGroupShowResp;

import java.util.List;


/**
 * 分组数据展示
 * Created by liu on 17/3/2.
 */

public class DtuGroupShowListAdapter extends BaseAdapter {

    private Context context;
    private List<DtuGroupShowResp.GroupData> list;

    public DtuGroupShowListAdapter(Context context, List<DtuGroupShowResp.GroupData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;

        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case 1:
                    viewHolderOne = new ViewHolderOne();
                    convertView = View.inflate(context, R.layout.adapter_dtu_group_show_lv_item2, null);
                    viewHolderOne.tvNameOne = (TextView) convertView.findViewById(R.id.tv_one_name);
                    viewHolderOne.tvValueOne = (TextView) convertView.findViewById(R.id.tv_one_value);
                    viewHolderOne.tvNameTwo = (TextView) convertView.findViewById(R.id.tv_two_name);
                    viewHolderOne.tvValueTwo = (TextView) convertView.findViewById(R.id.tv_two_value);
                    viewHolderOne.tvNameThree = (TextView) convertView.findViewById(R.id.tv_three_name);
                    viewHolderOne.tvValueThree = (TextView) convertView.findViewById(R.id.tv_three_value);
                    convertView.setTag(viewHolderOne);
                    break;
                case 0:
                    viewHolderTwo = new ViewHolderTwo();
                    convertView = View.inflate(context, R.layout.adapter_dtu_group_show_lv_item2, null);
                    viewHolderTwo.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                    viewHolderTwo.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
                    convertView.setTag(viewHolderTwo);
                    break;
            }
        } else {
            switch (type) {
                case 1:
                    viewHolderOne = (ViewHolderOne) convertView.getTag();
                    break;
                case 0:
                    viewHolderTwo = (ViewHolderTwo) convertView.getTag();
                    break;
            }
        }

        //
        switch (type) {
            case 1:

                viewHolderOne.tvNameOne.setText(list.get(0).getName());
                viewHolderOne.tvValueOne.setText(list.get(0).getValue()+list.get(0).getUnit());

                viewHolderOne.tvNameTwo.setText(list.get(1).getName());
                viewHolderOne.tvValueTwo.setText(list.get(1).getValue()+list.get(1).getUnit());

                viewHolderOne.tvNameThree.setText(list.get(2).getName());
                viewHolderOne.tvValueThree.setText(list.get(2).getValue()+list.get(2).getUnit());
                break;
            case 0:
                viewHolderTwo.tvName.setText(list.get(position).getName());
                viewHolderTwo.tvValue.setText(list.get(position).getValue()+list.get(position).getUnit());
                break;
        }

        return convertView;
    }

    class ViewHolderOne {
        TextView tvNameOne;
        TextView tvValueOne;
        TextView tvNameTwo;
        TextView tvValueTwo;
        TextView tvNameThree;
        TextView tvValueThree;

    }

    class ViewHolderTwo {
        TextView tvName;
        TextView tvValue;
    }
}
