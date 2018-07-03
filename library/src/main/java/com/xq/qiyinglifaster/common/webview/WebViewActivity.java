package com.xq.qiyinglifaster.common.webview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.xq.qiyinglifaster.base.base.MyBaseActivity;
import com.xq.qiyinglifaster.eventbus.ComponentEvent;

public class WebViewActivity extends MyBaseActivity<IWebViewView> implements IWebViewPresenter {

    public static final String KEY_URL = "url";

    private String url;

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        if (url != null)
        {
            getBindView().initToolbar("加载中...", true);

            getBindView().initWebView(url);
        }
    }

    @Override
    protected void resolveBundle(Bundle bundle) {
        //获取url
        url = bundle.getString(KEY_URL);
    }

    @Override
    protected IWebViewView createBindView() {
        return new WebViewView(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getBindView().getWebView().canGoBack())
        {
            getBindView().getWebView().goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
