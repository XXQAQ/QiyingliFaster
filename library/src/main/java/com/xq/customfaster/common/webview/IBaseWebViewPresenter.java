package com.xq.customfaster.common.webview;

import com.xq.customfaster.base.base.ICustomBasePresenter;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;

public interface IBaseWebViewPresenter extends ICustomBasePresenter<IBaseWebViewView>, IBaseRefreshLoadPresenter<IBaseWebViewView> {

    public static final String KEY_URL = "url";
    public static final String KEY_DATA = "data";
    public static final String KEY_DATABASEURL = "dataBaseUrl";

}
