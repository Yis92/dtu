package com.sixe.dtu.vm.index.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sixe.dtu.R;
import com.sixe.dtu.base.BaseActivity;
import com.sixe.dtu.base.BaseActivity2;


/**
 * 历史数据曲线展示
 * Created by Administrator on 2017/4/1.
 */

public class HistoryDataActivity extends BaseActivity {

    private TextView tvTitle;
    private Toolbar toolbar;
    private WebView webView;
    private ProgressBar pb;
    private String dtu_sn;

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
        pb = findView(R.id.pb);
        pb.setMax(100);
    }

    @Override
    public void initData(Intent intent) {

        Bundle bundle = intent.getExtras();
        dtu_sn = bundle.getString("dtu_sn");

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

        // 设置WebChromeClient，以支持运行特殊的Javascript
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

        });

//        webView.loadUrl("http://139.129.239.172:8080/comSys/home/goHisPage?nodeId=" + data_id + "&pId=" + group_id);
        webView.loadUrl("http://139.129.239.172:8080/comSys/home/goHisPage?nodeId=" + dtu_sn + "&mType=APP");
        Log.i("http", "历史数据请求的url：：：：http://139.129.239.172:8080/comSys/home/goHisPage?nodeId=" + dtu_sn + "&mType=APP");
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
