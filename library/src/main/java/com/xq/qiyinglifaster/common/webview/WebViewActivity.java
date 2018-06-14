package com.xq.qiyinglifaster.common.webview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.xq.qiyinglifaster.base.base.MyBaseActivity;
import com.xq.qiyinglifaster.eventbus.FourComponentsEvent;

public class WebViewActivity extends MyBaseActivity<IWebViewView> implements IWebViewPresenter {

    private String url;

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        if (url != null)
        {
            getBindView().initToolbar("加载中...", true);

            getBindView().initwebView(url);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void resolveBundle(Bundle bundle) {

        //获取url
        url = bundle.getString("url");
    }

    @Override
    protected void onFourCompoentsEvent(FourComponentsEvent event) {

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
