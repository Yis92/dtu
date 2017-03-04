package com.sixe.dtu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @FileName ScrollListView.java
 * @Description 嵌套在ScrollView中的ListView
 * @Package com.duobao.widget.scroll
 * @Version 1.0
 */
public class ScrollListView extends ListView {

	public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
