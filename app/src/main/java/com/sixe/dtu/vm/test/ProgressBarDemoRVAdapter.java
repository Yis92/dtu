package com.sixe.dtu.vm.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixe.dtu.R;
import com.sixe.dtu.widget.RoundProgressbarWithProgress;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ProgressBarDemoRVAdapter extends RecyclerView.Adapter<ProgressBarDemoRVAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.adapter_progress_bar_demo_rv_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        RoundProgressbarWithProgress progress;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
