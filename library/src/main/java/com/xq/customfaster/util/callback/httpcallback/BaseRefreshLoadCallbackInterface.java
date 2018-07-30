package com.xq.customfaster.util.callback.httpcallback;


import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.projectdefine.bean.behavior.ListBehavior;
import java.util.LinkedList;
import java.util.List;


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
    default void requestFinish(Object... objects) {
        BaseSimpleRefreshLoadCallbackInterface.super.requestFinish(objects);
    }

    default void operateSuccess(T t){
        List list = null;
        if (t instanceof List)
            list = (List) t;
        else    if (t instanceof ListBehavior)
            list = ((ListBehavior)t).getList();
        else
            return;

        if (getCallbackBuilder().refreshLoadView != null)
        {
            if (list == null)
                list = new LinkedList();

            if (list.size() <= 0)
                getCallbackBuilder().refreshLoadView.afterRefreshLoadEnd();

            if (getCallbackBuilder().refreshLoadBuilder.isRefresh)
                getCallbackBuilder().refreshLoadView.refreshView(list);
            else
                getCallbackBuilder().refreshLoadView.loadmoreView(list);
        }
    }

    public CallbackBuilder getCallbackBuilder();

    public static class CallbackBuilder extends BaseSimpleRefreshLoadCallbackInterface.CallbackBuilder{
        public IBaseRefreshLoadView refreshLoadView;
        public IBaseRefreshLoadPresenter.RefreshLoadBuilder refreshLoadBuilder;
    }

}

