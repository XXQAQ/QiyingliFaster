package com.xq.customfaster.common.webview;

import com.xq.customfaster.base.base.ICustomBaseView;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import java.util.Map;

public interface IWebViewView extends ICustomBaseView<IWebViewPresenter>, IBaseRefreshLoadView<IWebViewPresenter> {

    public void loadUrl(String url);

    public void loadData(String data);

    public void loadData(String data,String baseUrl);

    public void addJavascriptInterfaces(Map<String,String> map);

    //清除网页访问留下的缓存
    public void clearCache();

    //清除当前webview访问的历史记录
    public void clearHistory();

    //清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
    public void clearFormData();

    public void reload();

    public void goBack();

    public void goForward();

    public boolean canGoBack();

    public boolean canGoForward();

}
