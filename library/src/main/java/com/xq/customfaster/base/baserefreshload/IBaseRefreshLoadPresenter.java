package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.util.tools.ObjectUtils;

public interface IBaseRefreshLoadPresenter<T extends IBaseRefreshLoadView> extends IAbsRefreshLoadPresenter<T> {

    @Override
    default void startRefresh(){
        getRefreshLoadDelegate().startRefresh();
    }

    @Override
    default void startLoadmore(){
        getRefreshLoadDelegate().startLoadmore();
    }

    @Override
    default void refreshing(Object... objects) {
        getRefreshLoadDelegate().refreshing(objects);
    }

    @Override
    default void loadmoring(Object... objects) {
        getRefreshLoadDelegate().loadmoring(objects);
    }

    @Override
    default void cancleRefresh() {
        getRefreshLoadDelegate().cancleRefresh();
    }

    @Override
    default void cancleLoadmore() {
        getRefreshLoadDelegate().cancleLoadmore();
    }

    @Override
    default void refreshLoadData() {
        getRefreshLoadDelegate().refreshLoadData();
    }

    @Override
    default void refreshLoadData(Object object) {
        getRefreshLoadDelegate().refreshLoadData(object);
    }

    @Override
    default void refreshLoadData(Object object,boolean isSuccess) {
        getRefreshLoadDelegate().refreshLoadData(object,isSuccess);
    }

    @Override
    default boolean isRefresh() {
        return getRefreshLoadDelegate().isRefresh();
    }

    @Override
    default boolean isWorking() {
        return getRefreshLoadDelegate().isWorking();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadView> extends AbsPresenterDelegate<T> implements IAbsRefreshLoadPresenter<T> {

        protected int page = getFirstPage();
        protected boolean isRefresh = true;
        protected boolean isWorking = false;

        public RefreshLoadDelegate(IAbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void startRefresh(){
            getBindView().startRefresh();
        }

        @Override
        public void startLoadmore(){
            getBindView().startLoadmore();
        }

        @Override
        public void refreshing(Object... objects) {
            if (isWorking)
                return;

            isWorking = true;
            isRefresh = true;
            refreshLoad(true, getFirstPage(),objects);
        }

        @Override
        public void loadmoring(Object... objects) {
            if (isWorking)
                return;

            isWorking = true;
            isRefresh = false;
            refreshLoad(false, page + 1,objects);
        }

        @Override
        public void cancleRefresh() {

            isWorking = false;

            getBindView().afterRefresh();
        }

        @Override
        public void cancleLoadmore() {

            isWorking = false;

            getBindView().afterLoadmore();
        }

        @Override
        public void refreshLoadData() {

            isWorking = false;

            if (isRefresh)
            {
                page = getFirstPage();
                getBindView().refreshView(null);
            }
            else
            {
                page++;
                getBindView().loadmoreView(null);
            }

            if (isRefresh)
                getBindView().afterRefresh();
            else
                getBindView().afterLoadmore();
        }

        @Override
        public void refreshLoadData(Object object) {
            refreshLoadData(object,true);
        }

        @Override
        public void refreshLoadData(Object object, boolean isSuccess) {

            isWorking = false;

            if(isSuccess)
            {
                if (isEmptyData(object))
                {
                    if (isRefresh)
                        getBindView().refreshEmpty();
                    else
                        getBindView().loadmoreEmpty();
                }
                else
                {
                    if (isRefresh)
                    {
                        page = getFirstPage();
                        getBindView().refreshView(object);
                    }
                    else
                    {
                        page++;
                        getBindView().loadmoreView(object);
                    }
                }
            }
            else
            {
                if (isRefresh)
                    getBindView().refreshErro();
                else
                    getBindView().loadmoreErro();
            }

            if (isRefresh)
                getBindView().afterRefresh();
            else
                getBindView().afterLoadmore();
        }

        @Override
        public boolean isRefresh() {
            return isRefresh;
        }

        @Override
        public boolean isWorking() {
            return isWorking;
        }

        //判断对象是否含有数据(您需要根据需求重写该方法，因为page和emptyView需要本方法的返回值进行后续处理)
        protected boolean isEmptyData(Object object){
                return ObjectUtils.isEmpty(object);
        }

        //重写此方法可指定第一页下标
        protected int getFirstPage(){
            return 1;
        }

        //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
        protected abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    }
}
