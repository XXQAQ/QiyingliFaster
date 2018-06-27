package com.xq.qiyinglifaster.common.webview;


import android.webkit.WebView;

import com.xq.qiyinglifaster.base.base.IMyBaseView;

/**
 * Created by Administrator on 2017/9/18.
 */

public interface IWebViewView extends IMyBaseView<IWebViewPresenter> {

    public void initWebView(String url);

    public WebView getWebView();

}
