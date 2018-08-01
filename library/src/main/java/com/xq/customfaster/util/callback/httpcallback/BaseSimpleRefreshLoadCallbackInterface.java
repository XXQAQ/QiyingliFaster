package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadPresenter;
import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadView;
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
        if (getCallbackBuilder().refreshLoadView != null)
            getCallbackBuilder().refreshLoadView.afterRefreshLoadErro();
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        BaseCallbackInterface.super.requestFinish(t,objects);
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
        return BaseCallbackInterface.super.isEmpty(object);
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder extends BaseCallbackInterface.CallbackBuilder{
        public IBaseSimpleRefreshLoadView refreshLoadView;
        public IBaseSimpleRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData;

        public CallbackBuilder(IBaseSimpleRefreshLoadView refreshLoadView, IBaseSimpleRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData) {
            this.refreshLoadView = refreshLoadView;
            this.refreshLoadData = refreshLoadData;
        }
    }

}

