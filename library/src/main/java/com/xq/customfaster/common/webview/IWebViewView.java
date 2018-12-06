package com.xq.customfaster.common.webview;

import android.webkit.WebView;

import com.xq.customfaster.base.base.ICustomBaseView;

public interface IWebViewView extends ICustomBaseView<IWebViewPresenter> {

    public void initWebView(String url);

    public WebView getWebView();

}
