package com.xq.customfaster.common.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.xq.androidfaster.base.base.TopContainer;
import com.xq.androidfaster.util.tools.IntentUtils;
import com.xq.androidfaster.util.tools.NetworkUtils;
import com.xq.androidfaster.util.tools.PathUtils;
import com.xq.customfaster.R;
import com.xq.customfaster.base.base.CustomBaseView;

@TopContainer
public class WebViewView extends CustomBaseView<IWebViewPresenter> implements IWebViewView  {

    private WebView webView;
    private ProgressBar progressBar;

    public WebViewView(IWebViewPresenter presenter) {
        super(presenter);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);
        initWebView();
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

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void clearCache() {
        webView.clearCache(true);
    }

    @Override
    public void clearHistory() {
        webView.clearHistory();
    }

    @Override
    public void clearFormData() {
        webView.clearFormData();
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterfaces(Object[] objects) {
        for (Object object:objects)
            webView.addJavascriptInterface(object,object.getClass().getSimpleName());
    }

    @SuppressLint("MissingPermission")
    protected void initWebView(){

        WebSettings webSettings = webView.getSettings();
        //支持JS调用
        webSettings.setJavaScriptEnabled(true); //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //设置webview推荐使用的窗口，使html界面自适应屏幕
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setPluginState(WebSettings.PluginState.ON);    //支持Adobe插件
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH); //提高渲染的优先级
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //缓存策略，建议缓存策略为：判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetworkUtils.isConnected())
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        else
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        webSettings.setAppCachePath(PathUtils.getExternalAppCachePath());//设置  Application Caches 缓存目录

        webView.requestFocusFromTouch();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (view.getHitTestResult() == null)
                {
                    if (!TextUtils.isEmpty(url) && url.startsWith("http"))
                    {
                        view.loadUrl(url);
                        return true;
                    }
                    else
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        getContext().startActivity(intent);
                        return true;
                    }
                }

                return super.shouldOverrideUrlLoading(view,url);
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
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
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
                                        if (progressBar.getProgress() >=100)
                                            progressBar.setVisibility(View.INVISIBLE);
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
                if (webView.canGoBack()) webView.goBack();
            }
        });

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
