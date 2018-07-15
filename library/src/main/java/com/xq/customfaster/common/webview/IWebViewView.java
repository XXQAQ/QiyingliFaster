package com.xq.customfaster.common.webview;


import android.webkit.WebView;

import com.xq.customfaster.base.base.IMyBaseView;


public interface IWebViewView extends IMyBaseView<IWebViewPresenter> {

    public void initWebView(String url);

    public WebView getWebView();

}
