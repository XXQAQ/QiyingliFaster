package com.xq.customfaster.common.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.xq.androidfaster.base.base.TopContainer;
import com.xq.androidfaster.util.tools.IntentUtils;
import com.xq.androidfaster.util.tools.NetworkUtils;
import com.xq.androidfaster.util.tools.PathUtils;
import com.xq.customfaster.R;
import com.xq.customfaster.base.base.CustomBaseView;
import java.util.Map;

@TopContainer
public class BaseWebViewView extends CustomBaseView<IBaseWebViewPresenter> implements IBaseWebViewView {

    private WebView webView;
    private ProgressBar progressBar;

    public BaseWebViewView(IBaseWebViewPresenter presenter) {
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
        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.loadUrl("about:blank");
        webView.destroy();
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void loadData(String data) {
        loadData(data,null);
    }

    @Override
    public void loadData(String data, String baseUrl) {
        if (TextUtils.isEmpty(baseUrl))
            webView.loadData(data,"text/html;charset=UTF-8",null);
        else
            webView.loadDataWithBaseURL(baseUrl,data,"text/html;charset=UTF-8",null,null);
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

    @Override
    public void reload() {
        webView.reload();
    }

    @Override
    public void goBack() {
        webView.goBack();
    }

    @Override
    public void goForward() {
        webView.goForward();
    }

    @Override
    public boolean canGoBack() {
        return webView.canGoBack();
    }

    @Override
    public boolean canGoForward() {
        return webView.canGoForward();
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterfaces(Map<Object,String> map) {
        for (Object key:map.keySet())
            webView.addJavascriptInterface(key,map.get(key));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); //设置https网站可以访问http资源

        //缓存策略，建议缓存策略为：判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetworkUtils.isConnected())
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        else
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        webSettings.setAppCachePath(PathUtils.getInternalAppDataPath());//设置  Application Caches 缓存目录

        webView.requestFocusFromTouch();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                afterOnPageStarted();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                afterOnPageFinished();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(view.getHitTestResult() == null || view.getHitTestResult().getType() == WebView.HitTestResult.UNKNOWN_TYPE)
                {
                    return false;
                }
                if (!TextUtils.isEmpty(url) && url.startsWith("http"))
                {
                    view.loadUrl(url);
                    return true;
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (IntentUtils.isIntentAvailable(intent))
                    {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        getContext().startActivity(intent);
                        return true;
                    }
                    else
                    {
                        return super.shouldOverrideUrlLoading(view,url);
                    }
                }
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int l, int t, int oldl, int oldt) {
                    if (webView.getContentHeight() * webView.getScale() == (webView.getHeight() + webView.getScrollY())) //已经处于底端
                    {

                    }
                    if(webView.getScrollY() == 0)//处于顶端
                    {

                    }
                }
            });
        }
        getRefreshLoadDelegate().refreshLoadView.setEnableLoadmore(false);
    }

    protected void afterOnPageStarted(){

    }

    protected void afterOnPageFinished(){

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_webview;
    }

    private RefreshLoadDelegate refreshLoadDelegate = new RefreshLoadDelegate(this) {
        @Override
        public void refreshView(Object object) {
            super.refreshView(object);
            reload();
        }

        @Override
        public void loadmoreView(Object object) {
            super.loadmoreView(object);
        }
    };
    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate() {
        return refreshLoadDelegate;
    }
}
