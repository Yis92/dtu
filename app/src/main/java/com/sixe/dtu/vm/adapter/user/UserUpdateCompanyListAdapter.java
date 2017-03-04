//package com.sixe.dtu.vm.adapter.user;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.sixe.dtu.R;
//import com.sixe.dtu.http.entity.user.UserCompanyInfoResp;
//
//import java.util.List;
//
///**
// * 修改单位信息
// * 注意:使用Edittext不能复用viewholder
// * Created by liu on 17/2/28.
// */
//
//public class UserUpdateCompanyListAdapter extends BaseAdapter {
//
//    private Context context;
//    private List<UserCompanyInfoResp.UserCompanyInfo> list;
//
//    public List<UserCompanyInfoResp.UserCompanyInfo> getUpdateData() {
//        return list;
//    }
//
//    public UserUpdateCompanyListAdapter(Context context, List<UserCompanyInfoResp.UserCompanyInfo> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        //使用Edittext不能复用viewholder
//        ViewHolder viewHolder;
////        if (convertView == null) {
//        convertView = LayoutInflater.from(context).inflate(R.layout.adapter_user_update_cpmpany_lv_item, parent, false);
//        viewHolder = new ViewHolder();
//        viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
//        viewHolder.etValue = (EditText) convertView.findViewById(R.id.et_value);
//        viewHolder.llContent = (LinearLayout) convertView.findViewById(R.id.ll_content);
//        convertView.setTag(viewHolder);
////        } else {
//        viewHolder = (ViewHolder) convertView.getTag();
////        }
//
//        viewHolder.tvName.setText(list.get(position).getName());
//        viewHolder.etValue.setText(list.get(position).getValue());
//
//        if (position % 2 == 0) {
//            viewHolder.llContent.setBackgroundColor(Color.parseColor("#d5edf8"));
//        } else {
//            viewHolder.llContent.setBackgroundColor(Color.parseColor("#f5f5f5"));
//        }
//
//
//
//
//        viewHolder.etValue.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                list.get(position).setValue(s.toString());
//            }
//        });
//
//        return convertView;
//    }
//
//    class ViewHolder {
//        TextView tvName;
//        EditText etValue;
//        LinearLayout llContent;
//    }
//
//}
//
