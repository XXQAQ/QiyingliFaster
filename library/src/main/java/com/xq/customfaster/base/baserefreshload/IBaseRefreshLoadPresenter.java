package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.base.core.Controler;
import com.xq.androidfaster.base.delegate.BaseDelegate;
import com.xq.androidfaster.util.tools.ObjectUtils;

public interface IBaseRefreshLoadPresenter extends IBaseRefreshLoadBehavior {

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default void refresh(Object... objects) {
        getRefreshLoadDelegate().refresh(objects);
    }

    @Override
    default void loadmore(Object... objects) {
        getRefreshLoadDelegate().loadmore(objects);
    }

    @Override
    default void cancelRefresh() {
        getRefreshLoadDelegate().cancelRefresh();
    }

    @Override
    default void cancelLoadmore() {
        getRefreshLoadDelegate().cancelLoadmore();
    }

    @Override
    default void refreshLoadData(Object object) {
        getRefreshLoadDelegate().refreshLoadData(object);
    }

    @Override
    default void refreshLoadData(Object object, boolean isSuccess) {
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



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    @Deprecated
    @Override
    default void startRefresh(){
        getRefreshLoadDelegate().startRefresh();
    }

    @Deprecated
    @Override
    default void startLoadmore(){
        getRefreshLoadDelegate().startLoadmore();
    }

    @Deprecated
    @Override
    default void refreshView(Object object) {
        getRefreshLoadDelegate().refreshView(object);
    }

    @Deprecated
    @Override
    default void loadmoreView(Object object) {
        getRefreshLoadDelegate().loadmoreView(object);
    }

    @Deprecated
    @Override
    default void refreshEmpty() {
        getRefreshLoadDelegate().refreshEmpty();
    }

    @Deprecated
    @Override
    default void loadmoreEmpty(){
        getRefreshLoadDelegate().loadmoreEmpty();
    }

    @Deprecated
    @Override
    default void refreshErro() {
        getRefreshLoadDelegate().refreshErro();
    }

    @Deprecated
    @Override
    default void loadmoreErro() {
        getRefreshLoadDelegate().loadmoreErro();
    }

    @Deprecated
    @Override
    default void afterRefresh() {
        getRefreshLoadDelegate().afterRefresh();
    }

    @Deprecated
    @Override
    default void afterLoadmore() {
        getRefreshLoadDelegate().afterLoadmore();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate extends BaseDelegate implements IBaseRefreshLoadBehavior {

        private int page = getFirstPage();
        private boolean isRefresh = true;
        private boolean isWorking = false;

        public RefreshLoadDelegate(Controler controler) {
            super(controler);
        }

        @Override
        public void refresh(Object... objects) {
            if (isWorking)
                return;

            isWorking = true;
            isRefresh = true;
            refreshLoad(getFirstPage(),objects);
        }

        @Override
        public void loadmore(Object... objects) {
            if (isWorking)
                return;

            isWorking = true;
            isRefresh = false;
            refreshLoad(page + 1,objects);
        }

        @Override
        public void cancelRefresh() {

            isWorking = false;

            afterRefresh();
        }

        @Override
        public void cancelLoadmore() {

            isWorking = false;

            afterLoadmore();
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
                    if (isRefresh())
                        refreshEmpty();
                    else
                        loadmoreEmpty();
                }
                else
                {
                    if (isRefresh())
                    {
                        page = getFirstPage();
                        resolveRefreshData(object);
                        refreshView(object);
                    }
                    else
                    {
                        page++;
                        resolveLoadmoreData(object);
                        loadmoreView(object);
                    }
                }
            }
            else
            {
                if (isRefresh())
                    refreshErro();
                else
                    loadmoreErro();
            }

            if (isRefresh())
                afterRefresh();
            else
                afterLoadmore();
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
            return 0;
        }

        //在刷新View前需要处理的方法
        protected void resolveRefreshData(Object object){

        }

        //在加载View前需要处理的方法
        protected void resolveLoadmoreData(Object object){

        }

        //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
        protected abstract void refreshLoad(int page, Object... objects);



        ///////////////////////////////////////////////////////////////////////////
        // V
        ///////////////////////////////////////////////////////////////////////////
        @Deprecated
        @Override
        public void startRefresh() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).startRefresh();
        }

        @Deprecated
        @Override
        public void startLoadmore() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).startLoadmore();
        }

        @Deprecated
        @Override
        public void refreshView(Object object) {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshView(object);
        }

        @Deprecated
        @Override
        public void loadmoreView(Object object) {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).loadmoreView(object);
        }

        @Deprecated
        @Override
        public void refreshEmpty() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshEmpty();
        }

        @Deprecated
        @Override
        public void loadmoreEmpty() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).loadmoreEmpty();
        }

        @Deprecated
        @Override
        public void refreshErro() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshErro();
        }

        @Deprecated
        @Override
        public void loadmoreErro() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).loadmoreErro();
        }

        @Deprecated
        @Override
        public void afterRefresh() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).afterRefresh();
        }

        @Deprecated
        @Override
        public void afterLoadmore() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).afterLoadmore();
        }
    }
}
