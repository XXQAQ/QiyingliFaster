package com.xq.customfaster.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import java.util.ArrayList;
import java.util.List;

public class CustomRefreshLoadView extends TwinklingRefreshLayout implements RefreshLoadViewInterface {

    public CustomRefreshLoadView(Context context) {
        super(context);
        init();
    }

    public CustomRefreshLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRefreshLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOverScrollHeight(64);
        setPureScrollModeOn(true);
        setRefreshHeaderView(new ProgressLayout(getContext()));
        setLoadmoreFooterView( new LoadingView(getContext()));
    }

    private boolean isFirstRefresh = true;
    @Override
    public boolean isFirstRefresh() {
        return isFirstRefresh;
    }

    @Override
    public void setIsFirstRefresh(boolean isFirstRefresh) {
        this.isFirstRefresh = isFirstRefresh;
    }

    @Override
    public void setPureScrollModeOn(boolean isPureScrollMode){
        isPureScrollModeOn = isPureScrollMode;
    }

    @Override
    public void setRefreshHeaderView(Object o) {
        super.setHeaderView((IHeaderView) o);
    }

    @Override
    public void setLoadmoreFooterView(Object o) {
        super.setBottomView((IBottomView) o);
    }

    @Override
    public void startRefresh() {
        super.startRefresh();
    }

    @Override
    public void startLoadmore() {
        super.startLoadMore();
    }

    @Override
    public void finishRefresh() {
        super.finishRefreshing();
    }

    @Override
    public void finishLoadmore() {
        super.finishLoadmore();
    }

    @Override
    public void setEnableRefresh(boolean enableRefresh1) {
        super.setEnableRefresh(enableRefresh1);
    }

    @Override
    public void setEnableLoadmore(boolean enableLoadmore1) {
        super.setEnableLoadmore(enableLoadmore1);
    }

    @Override
    public void setOnRefreshLoadListener(OnRefreshLoadListener onRefreshLoadListener) {
        super.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefreshCanceled() {
                super.onRefreshCanceled();
            }

            @Override
            public void onLoadmoreCanceled() {
                super.onLoadmoreCanceled();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                onRefreshLoadListener.onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                onRefreshLoadListener.onLoadmore();
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
            }
        });
    }

    private StateLayout stateLayout;
    protected StateLayout getStateLayout(){
        if (stateLayout == null)
        {
            List<StateLayout> list = getAllSomeView(this,StateLayout.class);
            if (list == null || list.isEmpty()) return null;
            stateLayout = list.get(0);
        }
        return stateLayout;
    }

    //指定控件具体类型，获取Container容器下所有该类型的控件
    protected List getAllSomeView(View container, Class someView) {
        List list = new ArrayList<>();
        if (container instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) container;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View view = viewGroup.getChildAt(i);
                if (someView.isAssignableFrom(view.getClass()))
                    list.add(view);
                //再次 调用本身（递归）
                list.addAll(getAllSomeView(view,someView));
            }
        }
        return list;
    }

    @Override
    public void setLoadingView(Object o) {
        if (getStateLayout() != null)
            getStateLayout().addStateView(StateLayout.STATE_LOADING, (View) o);
    }

    @Override
    public void showLoading() {
        if (getStateLayout() != null)
            getStateLayout().showStateView(StateLayout.STATE_LOADING);
    }

    @Override
    public void showContent() {
        if (getStateLayout() != null)
            getStateLayout().showStateView(StateLayout.STATE_CONTENT);
    }

    @Override
    public void setEmptyView(Object o) {
        if (getStateLayout() != null)
            getStateLayout().addStateView(StateLayout.STATE_EMPTY, (View) o);
    }

    @Override
    public void showEmpty() {
        if (getStateLayout() != null)
            getStateLayout().showStateView(StateLayout.STATE_EMPTY);
    }

    @Override
    public void setErroView(Object o) {
        if (getStateLayout() != null)
            getStateLayout().addStateView(StateLayout.STATE_ERRO, (View) o);
    }

    @Override
    public void showErro() {
        if (getStateLayout() != null)
            getStateLayout().showStateView(StateLayout.STATE_ERRO);
    }

}
