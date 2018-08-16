package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.basesimplerefreshload.IBaseRefreshLoadPresenter;
import com.xq.projectdefine.util.callback.httpcallback.AbsCallback;


public interface AbsRefreshLoadCallback<T> extends AbsCallback<T> {

    default void requestStart(Object... objects) {
        AbsCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        AbsCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        AbsCallback.super.requestError(objects);
        getCallbackBuilder().presenter.refreshLoadErro();
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        AbsCallback.super.requestFinish(t,objects);
        getCallbackBuilder().presenter.refreshLoadData(getCallbackBuilder().isOperateSuccess,t);
    }

    default void operateSuccess(T t) {

    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder extends AbsCallback.CallbackBuilder{
        public IBaseRefreshLoadPresenter presenter;

        public CallbackBuilder(IBaseRefreshLoadPresenter presenter) {
            this.presenter = presenter;
        }
    }

}

