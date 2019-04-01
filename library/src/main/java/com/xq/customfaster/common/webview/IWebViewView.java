package com.xq.customfaster.common.webview;

import android.webkit.WebView;
import com.xq.customfaster.base.base.ICustomBaseView;

public interface IWebViewView extends ICustomBaseView<IWebViewPresenter> {

    public void loadUrl(String url);

//    public void addJavascriptInterfaces();

    //清除网页访问留下的缓存
    public void clearCache();

    //清除当前webview访问的历史记录
    public void clearHistory();

    //清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
    public void clearFormData();

    public WebView getWebView();

}
