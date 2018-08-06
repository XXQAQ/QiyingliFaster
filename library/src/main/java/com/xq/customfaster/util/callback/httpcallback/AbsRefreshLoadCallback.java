package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.baserefreshload.AbsRefreshLoadView;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.ListBehavior;


public interface AbsRefreshLoadCallback<T> extends AbsSimpleRefreshLoadCallback<T> {

    default void requestStart(Object... objects) {
        AbsSimpleRefreshLoadCallback.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        AbsSimpleRefreshLoadCallback.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        AbsSimpleRefreshLoadCallback.super.requestError(objects);
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        AbsSimpleRefreshLoadCallback.super.requestFinish(t,objects);
    }

    @Override
    default boolean isEmpty(Object object) {
        boolean isEmpty = AbsSimpleRefreshLoadCallback.super.isEmpty(object);
        if (isEmpty)
            return isEmpty;
        else
            if (object instanceof ListBehavior)
                return AbsSimpleRefreshLoadCallback.super.isEmpty(((ListBehavior) object).getList());
            else
                return false;
    }

    public CallbackBuilder getCallbackBuilder();

    public class CallbackBuilder extends AbsSimpleRefreshLoadCallback.CallbackBuilder{

        public IBaseRefreshLoadView refreshLoadView;
        public IBaseRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData;

        public CallbackBuilder(IBaseRefreshLoadView refreshLoadView, IBaseRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData) {
            super(refreshLoadView, refreshLoadData);
            this.refreshLoadView = refreshLoadView;
            this.refreshLoadData = refreshLoadData;
        }
    }

}
