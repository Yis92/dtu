package com.sixe.dtu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 重写ExpandableListView以解决ScrollView嵌套ExpandableListView
 * <br> 导致ExpandableListView显示不正常的问题
 *
 * @author liu
 *         Date:2017.3.1
 */
public class CustomExpandableListView extends ExpandableListView {

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}