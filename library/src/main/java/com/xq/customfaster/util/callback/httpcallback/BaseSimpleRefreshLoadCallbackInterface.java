package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadPresenter;
import com.xq.projectdefine.util.callback.httpcallback.BaseCallbackInterface;


public interface BaseSimpleRefreshLoadCallbackInterface<T> extends BaseCallbackInterface<T> {

    default void requestStart(Object... objects) {
        BaseCallbackInterface.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        BaseCallbackInterface.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        BaseCallbackInterface.super.requestError(objects);
        if (getCallbackBuilder().simpleRefreshLoadView != null)
            getCallbackBuilder().simpleRefreshLoadView.afterRefreshLoadErro();
    }

    @Override
    default void requestFinish(Object... objects) {
        BaseCallbackInterface.super.requestFinish(objects);
        if (getCallbackBuilder().simpleRefreshLoadView != null)
        {
            if (getCallbackBuilder().simpleRefreshLoadBuilder.isRefresh)
            {
                if (!getCallbackBuilder().isEmpty)
                    getCallbackBuilder().simpleRefreshLoadBuilder.page = 1;
                getCallbackBuilder().simpleRefreshLoadView.afterRefresh();
            }
            else
            {
                if (!getCallbackBuilder().isEmpty)
                    getCallbackBuilder().simpleRefreshLoadBuilder.page++;
                getCallbackBuilder().simpleRefreshLoadView.afterLoadmore();
            }

            if (getCallbackBuilder().isEmpty)
                getCallbackBuilder().simpleRefreshLoadView.afterEmpty();
        }
    }

    default void operateSuccess(T t) {
        if (getCallbackBuilder().simpleRefreshLoadView != null)
        {
            if (getCallbackBuilder().isEmpty)
                getCallbackBuilder().simpleRefreshLoadView.afterRefreshLoadEnd();

            if (getCallbackBuilder().simpleRefreshLoadBuilder.isRefresh)
                getCallbackBuilder().simpleRefreshLoadView.refreshView(t);
            else
                getCallbackBuilder().simpleRefreshLoadView.loadmoreView(t);
        }
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder extends BaseCallbackInterface.CallbackBuilder{
        public IBaseRefreshLoadView simpleRefreshLoadView;
        public IBaseSimpleRefreshLoadPresenter.RefreshLoadBuilder simpleRefreshLoadBuilder;
    }

}

