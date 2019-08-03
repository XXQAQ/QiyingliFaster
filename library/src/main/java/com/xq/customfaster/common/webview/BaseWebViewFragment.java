package com.xq.customfaster.common.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.xq.androidfaster.util.tools.ResourceUtils;
import com.xq.customfaster.R;
import com.xq.customfaster.base.base.CustomBaseFragment;

public class BaseWebViewFragment extends CustomBaseFragment<IBaseWebViewView> implements IBaseWebViewPresenter {

    private String url;
    private String data;
    private String dataBaseUrl;

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        super.create(savedInstanceState);

        getBindView().initToolbar(ResourceUtils.getString(R.string.loading));

        if (!TextUtils.isEmpty(data))
        {
            if (!TextUtils.isEmpty(dataBaseUrl))
                getBindView().loadData(data,dataBaseUrl);
            else
                getBindView().loadData(data);
        }
        if (!TextUtils.isEmpty(url))
        {
            getBindView().loadUrl(url);
        }
    }

    @Override
    public void resolveBundle(Bundle bundle) {
        //获取url
        url = bundle.getString(KEY_URL);
        data = bundle.getString(KEY_DATA);
        dataBaseUrl = bundle.getString(KEY_DATABASEURL);
    }

    @Override
    protected IBaseWebViewView createBindView() {
        return new BaseWebViewView(this);
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
        protected void refreshLoad(int page, Object... objects) {

        }
    };
    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate() {
        return refreshLoadDelegate;
    }
}
