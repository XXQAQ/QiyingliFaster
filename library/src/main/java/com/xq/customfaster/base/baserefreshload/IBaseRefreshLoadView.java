package com.xq.customfaster.base.baserefreshload;

import android.os.Bundle;
import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.base.core.Controler;
import com.xq.androidfaster.base.delegate.BaseDelegate;
import com.xq.androidfaster.util.tools.ToastUtils;
import com.xq.customfaster.base.base.ICustomBaseBehavior;
import com.xq.customfaster.widget.view.RefreshLoadViewInterface;

public interface IBaseRefreshLoadView extends IBaseRefreshLoadBehavior {

    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default void startRefresh(){
        getRefreshLoadDelegate().startRefresh();
    }

    @Override
    default void startLoadmore(){
        getRefreshLoadDelegate().startLoadmore();
    }

    @Override
    default void refreshView(Object object) {
        getRefreshLoadDelegate().refreshView(object);
    }

    @Override
    default void loadmoreView(Object object) {
        getRefreshLoadDelegate().loadmoreView(object);
    }

    @Override
    default void refreshEmpty() {
        getRefreshLoadDelegate().refreshEmpty();
    }

    @Override
    default void loadmoreEmpty(){
        getRefreshLoadDelegate().loadmoreEmpty();
    }

    @Override
    default void refreshErro() {
        getRefreshLoadDelegate().refreshErro();
    }

    @Override
    default void loadmoreErro() {
        getRefreshLoadDelegate().loadmoreErro();
    }

    @Override
    default void afterRefresh() {
        getRefreshLoadDelegate().afterRefresh();
    }

    @Override
    default void afterLoadmore() {
        getRefreshLoadDelegate().afterLoadmore();
    }



    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    @Deprecated
    @Override
    default boolean isRefresh() {
        return getRefreshLoadDelegate().isRefresh();
    }

    @Deprecated
    @Override
    default boolean isWorking() {
        return getRefreshLoadDelegate().isWorking();
    }

    @Deprecated
    @Override
    default void refresh(Object... objects) {
        getRefreshLoadDelegate().refresh(objects);
    }

    @Deprecated
    @Override
    default void loadmore(Object... objects) {
        getRefreshLoadDelegate().loadmore(objects);
    }

    @Deprecated
    @Override
    default void cancelRefresh() {
        getRefreshLoadDelegate().cancelRefresh();
    }

    @Deprecated
    @Override
    default void cancelLoadmore() {
        getRefreshLoadDelegate().cancelLoadmore();
    }

    @Deprecated
    @Override
    default void refreshLoadData(Object object) {
        getRefreshLoadDelegate().refreshLoadData(object);
    }

    @Deprecated
    @Override
    default void refreshLoadData(Object object, boolean isSuccess) {
        getRefreshLoadDelegate().refreshLoadData(object,isSuccess);
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public class RefreshLoadDelegate extends BaseDelegate implements IBaseRefreshLoadBehavior, RefreshLoadViewInterface.OnRefreshLoadListener {

        public RefreshLoadDelegate(Controler controler) {
            super(controler);
        }

        @Override
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            //以下初始化刷新控件
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null)
            {
                ((ICustomBaseBehavior)getControler()).getRefreshLoadView().setPureScrollModeOn(false);
                ((ICustomBaseBehavior)getControler()).getRefreshLoadView().setOnRefreshLoadListener(this);
                if (getLoadingView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().setLoadingView(getLoadingView());
                if (getEmptyView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().setEmptyView(getEmptyView());
                if (getErroView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().setErroView(getErroView());
            }
        }

        @Override
        public void startRefresh(){
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null)
                if (!((ICustomBaseBehavior)getControler()).getRefreshLoadView().isFirstRefresh())
                {
                    ((ICustomBaseBehavior)getControler()).getRefreshLoadView().startRefresh();
                }
                else
                {
                    ((ICustomBaseBehavior)getControler()).getRefreshLoadView().showLoading();
                    onRefresh();
                }
            else
                onRefresh();
        }

        @Override
        public void startLoadmore(){
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null)
                ((ICustomBaseBehavior)getControler()).getRefreshLoadView().startLoadmore();
            else
                onLoadmore();
        }

        @Override
        public void refreshView(Object object){
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().showContent();
        }

        @Override
        public void loadmoreView(Object object) {

        }

        @Override
        public void refreshEmpty() {
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().showEmpty();
        }

        @Override
        public void loadmoreEmpty() {
            ToastUtils.showShort("没有更多数据啦");
        }

        @Override
        public void refreshErro() {
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().showErro();
        }

        @Override
        public void loadmoreErro() {
            ToastUtils.showShort("数据加载失败");
        }

        @Override
        public void afterRefresh() {
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null)
            {
                ((ICustomBaseBehavior)getControler()).getRefreshLoadView().finishRefresh();
                ((ICustomBaseBehavior)getControler()).getRefreshLoadView().setIsFirstRefresh(false);
            }
        }

        @Override
        public void afterLoadmore() {
            if (((ICustomBaseBehavior)getControler()).getRefreshLoadView() != null) ((ICustomBaseBehavior)getControler()).getRefreshLoadView().finishLoadmore();
        }

        public Object getLoadingView(){
            return null;
        }

        //获取空布局
        protected Object getEmptyView() {
            return null;
        }

        //获取错误布局
        protected Object getErroView() {
            return null;
        }

        //以下为刷新加载的监听器
        @Override
        public void onRefresh() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refresh();
        }

        @Override
        public void onLoadmore() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).loadmore();
        }



        ///////////////////////////////////////////////////////////////////////////
        // P
        ///////////////////////////////////////////////////////////////////////////
        @Deprecated
        @Override
        public boolean isRefresh() {
            return ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).isRefresh();
        }

        @Deprecated
        @Override
        public boolean isWorking() {
            return ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).isWorking();
        }

        @Deprecated
        @Override
        public void refresh(Object... objects) {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refresh(objects);
        }

        @Deprecated
        @Override
        public void loadmore(Object... objects) {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).loadmore(objects);
        }

        @Deprecated
        @Override
        public void cancelRefresh() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).cancelRefresh();
        }

        @Deprecated
        @Override
        public void cancelLoadmore() {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).cancelLoadmore();
        }

        @Deprecated
        @Override
        public void refreshLoadData(Object object) {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshLoadData(object);
        }

        @Deprecated
        @Override
        public void refreshLoadData(Object object, boolean isSuccess) {
            ((IBaseRefreshLoadBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshLoadData(object,isSuccess);
        }

    }

}
