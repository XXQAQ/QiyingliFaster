package com.xq.customfaster.util.callback;

import com.xq.androidfaster.util.callback.FasterHttpCallback;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadBehavior;

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
        getCallbackBuilder().behavior.refreshLoadData(getCallbackBuilder().data,getCallbackBuilder().isOperateSuccess);
    }

    default void operateSuccess(T t) {

    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder extends FasterHttpCallback.CallbackBuilder{

        public IBaseRefreshLoadBehavior behavior;

        public CallbackBuilder(IBaseRefreshLoadBehavior behavior) {
            this.behavior = behavior;
        }
    }

}

