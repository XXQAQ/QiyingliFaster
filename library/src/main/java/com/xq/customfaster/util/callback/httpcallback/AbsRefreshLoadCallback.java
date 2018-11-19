package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import com.xq.projectdefine.util.callback.httpcallback.FasterHttpCallback;


public interface AbsRefreshLoadCallback<T> extends FasterHttpCallback<T> {

    default void requestStart(Object... objects) {
        FasterHttpCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        FasterHttpCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        FasterHttpCallback.super.requestError(objects);
        getCallbackBuilder().presenter.refreshLoadErro();
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        FasterHttpCallback.super.requestFinish(t,objects);
        getCallbackBuilder().presenter.refreshLoadData(getCallbackBuilder().isOperateSuccess,t);
    }

    default void operateSuccess(T t) {

    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder extends FasterHttpCallback.CallbackBuilder{
        public IBaseRefreshLoadPresenter presenter;

        public CallbackBuilder(IBaseRefreshLoadPresenter presenter) {
            this.presenter = presenter;
        }
    }

}

