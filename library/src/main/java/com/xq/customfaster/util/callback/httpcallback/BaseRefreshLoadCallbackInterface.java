package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.ListBehavior;


public interface BaseRefreshLoadCallbackInterface<T> extends BaseSimpleRefreshLoadCallbackInterface<T> {

    default void requestStart(Object... objects) {
        BaseSimpleRefreshLoadCallbackInterface.super.requestStart(objects);
    }

    default void requestSuccess(T t, Object... objects) {
        BaseSimpleRefreshLoadCallbackInterface.super.requestSuccess(t,objects);
    }

    default void requestError(Object... objects) {
        BaseSimpleRefreshLoadCallbackInterface.super.requestError(objects);
    }

    @Override
    default void requestFinish(T t,Object... objects) {
        BaseSimpleRefreshLoadCallbackInterface.super.requestFinish(t,objects);
    }

    @Override
    default boolean isEmpty(Object object) {
        boolean isEmpty = BaseSimpleRefreshLoadCallbackInterface.super.isEmpty(object);
        if (isEmpty)
            return isEmpty;
        else
            if (object instanceof ListBehavior)
                return BaseSimpleRefreshLoadCallbackInterface.super.isEmpty(((ListBehavior) object).getList());
            else
                return false;
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder extends BaseSimpleRefreshLoadCallbackInterface.CallbackBuilder{

        public IBaseRefreshLoadView refreshLoadView;
        public IBaseRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData;

        public CallbackBuilder(IBaseRefreshLoadView refreshLoadView, IBaseRefreshLoadPresenter.RefreshLoadDelegate refreshLoadData) {
            super(refreshLoadView, refreshLoadData);
            this.refreshLoadView = refreshLoadView;
            this.refreshLoadData = refreshLoadData;
        }
    }

}

