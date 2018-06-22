package com.xq.qiyinglifaster.common.webview;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.xq.qiyinglifaster.R;
import com.xq.qiyinglifaster.base.base.MyBaseView;
import com.xq.qiyinglifaster.util.SDUtils;
import java.io.File;

public class WebViewView extends MyBaseView<IWebViewPresenter> implements IWebViewView  {

    private TwinklingRefreshLayout refreshView;
    private WebView webView;
    private ProgressBar pb;

    public WebViewView(IWebViewPresenter presenter) {
        super(presenter);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        findView();

    }

    @Override
    public void onResume() {
        super.onResume();
        getWebView().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getWebView().onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getWebView().destroy();
    }

    private void findView() {
        refreshView = (TwinklingRefreshLayout)findViewById(R.id.refreshView);
        webView = (WebView)findViewById(R.id.webView);
        pb = (ProgressBar)findViewById(R.id.pb);
    }

    @Override
    public void initwebView(String url){

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        // 开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 设置数据库缓存路径
        // 开启database storage API功能
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(SDUtils.getCacheDir(getContext())+ File.pathSeparatorChar+"web"); // API 19 deprecated
        // 设置Application caches缓存目录
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(SDUtils.getCacheDir(getContext())+ File.pathSeparatorChar+"web");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webView.setNestedScrollingEnabled(false);
        webView.requestFocusFromTouch();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //WebView跳转
                if (!TextUtils.isEmpty(url) && view.getHitTestResult() == null)
                {
                    view.loadUrl(url);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                initToolbar(title,true);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100)
                {
                    pb.setVisibility(View.GONE);
                }
                else
                {
                    pb.setVisibility(View.VISIBLE);
                    pb.setProgress(newProgress);
                }

            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (webView.canGoBack() == false)
                {
                    ((Activity)getContext()).onBackPressed();
                }
            }
        });

        webView.loadUrl(url);
    }


    @Override
    protected boolean isHideSystemBar() {
        return true;
    }

    @Override
    public WebView getWebView() {
        return webView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }
}
