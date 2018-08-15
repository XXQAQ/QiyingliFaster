package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadView;
import com.xq.projectdefine.bean.entity.SuccessBean;
import com.xq.projectdefine.util.callback.SuccessCallback;
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

            if (getCallbackBuilder().isRefresh)
            {
                getCallbackBuilder().refreshLoadView.refreshView(t);
                getCallbackBuilder().refreshLoadView.afterRefresh();
            }
            else
            {
                getCallbackBuilder().refreshLoadView.loadmoreView(t);
                getCallbackBuilder().refreshLoadView.afterLoadmore();
            }

            if (!isEmpty(t))
                getCallbackBuilder().callback.onCallback(new SuccessBean(true));
            else
                getCallbackBuilder().callback.onCallback(new SuccessBean(false));
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
        public boolean isRefresh;
        public IBaseSimpleRefreshLoadView refreshLoadView;
        public SuccessCallback callback;

        public CallbackBuilder(boolean isRefresh,IBaseSimpleRefreshLoadView refreshLoadView, SuccessCallback callback) {
            this.refreshLoadView = refreshLoadView;
            this.callback = callback;
            this.isRefresh = isRefresh;
        }
    }

}

