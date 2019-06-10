package com.xq.customfaster.util.callback.httpcallback;

import com.xq.androidfaster.util.FasterHttpCallback;
import com.xq.customfaster.base.baserefreshload.IAbsRefreshLoadPresenter;

public interface RefreshLoadCallbackInterface<T> extends FasterHttpCallback<T> {

    default void requestStart(Object... objects) {
        FasterHttpCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        FasterHttpCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        FasterHttpCallback.super.requestError(objects);
    }

    @Override
    default void requestFinish(Object... objects) {
        FasterHttpCallback.super.requestFinish(objects);
        getCallbackBuilder().presenter.refreshLoadData(getCallbackBuilder().data,getCallbackBuilder().isOperateSuccess);
    }

    default void operateSuccess(T t) {

    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder extends FasterHttpCallback.CallbackBuilder{

        public IAbsRefreshLoadPresenter presenter;

        public CallbackBuilder(IAbsRefreshLoadPresenter presenter) {
            this.presenter = presenter;
        }
    }

}

