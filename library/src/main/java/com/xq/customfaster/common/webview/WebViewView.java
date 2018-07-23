package com.xq.customfaster.common.webview;


import android.annotation.SuppressLint;
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
import com.xq.projectdefine.base.base.TopContainer;
import com.xq.projectdefine.util.tools.IntentUtils;
import com.xq.projectdefine.util.tools.NetworkUtils;
import com.xq.projectdefine.util.tools.PathUtils;
import com.xq.customfaster.R;
import com.xq.customfaster.base.base.CustomBaseView;

@TopContainer
public class WebViewView extends CustomBaseView<IWebViewPresenter> implements IWebViewView  {

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

    @SuppressLint("MissingPermission")
    @Override
    public void initWebView(String url){

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetworkUtils.isConnected())
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        else
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 设置数据库缓存路径
        // 开启database storage API功能
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(PathUtils.getAppIntDbPath("webView")); // API 19 deprecated
        // 设置Application caches缓存目录
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(PathUtils.getAppExtCachePath());

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
                if (newProgress <= 100)
                {
                    pb.setVisibility(View.VISIBLE);
                    pb.setProgress(newProgress);
                }
                if (newProgress >= 100)
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(300);
                                ((Activity)getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (pb.getProgress() >=100)
                                            pb.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                getContext().startActivity(IntentUtils.getWebIntent(url));
                if (webView.canGoBack() == false)
                {
                    ((Activity)getContext()).onBackPressed();
                }
            }
        });

        webView.loadUrl(url);
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
