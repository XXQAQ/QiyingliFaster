package com.xq.customfaster.common.webview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import com.xq.customfaster.base.base.CustomBaseFragment;

public class WebViewFragment extends CustomBaseFragment<IWebViewView> implements IWebViewPresenter {

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

}
