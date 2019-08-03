package com.xq.customfaster.base.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import com.xq.androidfaster.base.base.FasterBaseFragment;
import com.xq.androidfaster.util.tools.ResourceUtils;
import com.xq.customfaster.util.event.ComponentEvent;
import com.xq.customfaster.widget.view.CustomRefreshLoadView;
import com.xq.worldbean.util.EventManager;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

public abstract class CustomBaseFragment<T extends ICustomBaseBehavior> extends FasterBaseFragment<T> implements ICustomBaseBehavior<T> {

    @Override
    protected T createBindPresenter() {
        return null;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);

        EventManager.regist(this);

        if (getLayoutId() != 0)
            initCommonView(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        EventManager.unRegist(this);
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkComponentEvent(ComponentEvent event){
        if (!event.getDestCommunicator().getComponentName().equalsIgnoreCase(getClass().getName()))
            return;
        else
            onComponentEvent(event);
    }

    //当接受到传递给本Fragment的事件时回调的方法
    protected void onComponentEvent(ComponentEvent event){

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    private Toolbar toolbar;
    @Override
    public Toolbar getToolbar() {
        if (toolbar == null)
        {
            toolbar = findViewById(ResourceUtils.getIdByName("toolbar"));
            if (toolbar != null)    return toolbar;
            List<Toolbar> list = getAllSomeView(getRootView(), Toolbar.class);
            if (list != null && !list.isEmpty()) toolbar = list.get(0);
        }
        return toolbar;
    }

    private ViewGroup barLayout;
    @Override
    public ViewGroup getBarLayout() {
        if (barLayout == null)
        {
            barLayout = findViewById(ResourceUtils.getIdByName("barLayout"));
            if (barLayout != null)  return barLayout;
            List<ViewGroup> list = getAllSomeView(getRootView(), AppBarLayout.class);
            if (list != null && !list.isEmpty()) barLayout = list.get(0);
        }
        return barLayout;
    }

    private CustomRefreshLoadView refreshLoadView;
    @Override
    public CustomRefreshLoadView getRefreshLoadView() {
        if (refreshLoadView == null)
        {
            refreshLoadView = findViewById(ResourceUtils.getIdByName("refreshLoadView"));
            if (refreshLoadView != null)  return refreshLoadView;
            List<CustomRefreshLoadView> list = getAllSomeView(getRootView(), CustomRefreshLoadView.class);
            if (list != null && !list.isEmpty()) refreshLoadView = list.get(0);
        }
        return refreshLoadView;
    }

}
