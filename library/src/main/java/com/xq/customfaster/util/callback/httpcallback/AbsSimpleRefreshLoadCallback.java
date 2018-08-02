package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.basesimplerefreshload.AbsSimpleRefreshLoadView;
import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadPresenter;
import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadView;
import com.xq.projectdefine.util.callback.httpcallback.AbsCallback;


public interface AbsSimpleRefreshLoadCallback<T> extends AbsCallback<T> {

    default void requestStart(Object... objects) {
        AbsCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        AbsCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        AbsCallback.super.requestError(objects);
        if (getCallbackBuilder().refreshLoadView != null)
            getCallbackBuilder().refreshLoadView.afterRefreshLoadErro();
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        AbsCallback.super.requestFinish(t,objects);
        if (getCallbackBuilder().refreshLoadView != null)
        {

            if (isEmpty(t))
            {
                getCallbackBuilder().refreshLoadView.afterEmpty();
                getCallbackBuilder().refreshLoadView.afterRefreshLoadEnd();
            }

            if (getCallbackBuilder().refreshLoadData.isRefresh)
            {
                if (!isEmpty(t))
                    getCallbackBuilder().refreshLoadData.page = getCallbackBuilder().refreshLoadData.getFirstPage();
                getCallbackBuilder().refreshLoadView.refreshView(t);
                getCallbackBuilder().refreshLoadView.afterRefresh();
            }
            else
            {
                if (!isEmpty(t))
                    getCallbackBuilder().refreshLoadData.page++;
                getCallbackBuilder().refreshLoadView.loadmoreView(t);
                getCallbackBuilder().refreshLoadView.afterLoadmore();
            }
        }
    }

    default void operateSuccess(T t) {

    }

    @Override
    default boolean isEmpty(Object object) {
        return AbsCallback.super.isEmpty(object);
    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder extends AbsCallback.CallbackBuilder{
        public IBaseSimpleRefreshLoadView refreshLoadView;
        public IBaseSimpleRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData;

        public CallbackBuilder(IBaseSimpleRefreshLoadView refreshLoadView, IBaseSimpleRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData) {
            this.refreshLoadView = refreshLoadView;
            this.refreshLoadData = refreshLoadData;
        }
    }

}

