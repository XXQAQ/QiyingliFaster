package com.xq.customfaster.base.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import com.xq.androidfaster.base.base.FasterBaseActivity;
import com.xq.androidfaster.util.tools.KeyboardUtils;
import com.xq.androidfaster.util.tools.ResourceUtils;
import com.xq.customfaster.util.event.ComponentEvent;
import com.xq.customfaster.widget.view.CustomRefreshLoadView;
import com.xq.worldbean.util.EventManager;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

public abstract class CustomBaseActivity<T extends ICustomBaseBehavior> extends FasterBaseActivity<T> implements ICustomBaseBehavior<T> {

    @Override
    protected T createBindPresenter() {
        return null;
    }

    @Deprecated
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);

        EventManager.regist(this);

        if (getLayoutId() != 0)
            initCommonView(this);
    }

    @Override
    public void visible() {
        super.visible();
        KeyboardUtils.hideSoftInput(this);
    }

    @Deprecated
    @Override
    public void destroy() {
        super.destroy();
        EventManager.unRegist(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkComponentEvent(ComponentEvent event){
        if (!event.getDestCommunicator().getComponentName().equalsIgnoreCase(getClass().getName()))
            return;
        else
            onComponentEvent(event);
    }

    //当接受到传递给本Activity的事件时回调的方法
    protected void onComponentEvent(ComponentEvent event) {

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
