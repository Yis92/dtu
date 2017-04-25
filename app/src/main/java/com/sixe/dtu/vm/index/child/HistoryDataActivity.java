package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.base.BaseActivity2;


/**
 * 历史数据曲线展示
 * Created by Administrator on 2017/4/1.
 */

public class HistoryDataActivity extends BaseActivity2 {

    private TextView tvTitle;
    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_history_data);
    }


    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        tvTitle = findView(R.id.tv_title);
        toolbar = findView(R.id.tool_bar);
        webView = findView(R.id.web_view);
    }

    @Override
    public void initData(Intent intent) {
        tvTitle.requestFocus();
        toolbar.setNavigationIcon(R.mipmap.white_back);

        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
//        webView.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕-在展示视频时,红米1s中无法展示,故此去掉
//        mWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWeb.getSettings().setLoadWithOverviewMode(true);
        //设置此属性，可任意比例缩放。
        //mWeb.getSettings().setUseWideViewPort(true);
        // 设置WebViewClient，保证新的链接地址不打开系统的浏览器窗口
        webView.setWebViewClient(new WebViewClient());
        // 设置WebView支持运行普通的Javascript
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("http://139.129.239.172:8080/comSys/dtuHome/goHisPage?nodeId=1512110003000001&pId=2");
    }

    @Override
    public void initEvents() {
        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public Class<?> getClazz() {
        return HistoryDataActivity.class;
    }
}
