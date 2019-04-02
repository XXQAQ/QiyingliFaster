package com.xq.customfaster.common.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.xq.customfaster.R;
import com.xq.customfaster.base.base.CustomBaseFragment;

public class WebViewFragment extends CustomBaseFragment<IWebViewView> implements IWebViewPresenter {

    public static final String KEY_URL = "url";

    private String url;

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        if (!TextUtils.isEmpty(url))
        {
            getBindView().initToolbar(getString(R.string.loading), true);

            getBindView().loadUrl(url);
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
    public boolean onBackClick() {
        if (getBindView().canGoBack())
        {
            getBindView().goBack();// 返回前一个页面
            return true;
        }
        else
            return false;
    }

    private RefreshLoadDelegate refreshLoadDelegate = new RefreshLoadDelegate(this) {
        @Override
        protected void refreshLoad(boolean isRefresh, int page, Object... objects) {
            refreshLoadData();
        }
    };
    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate() {
        return refreshLoadDelegate;
    }
}
