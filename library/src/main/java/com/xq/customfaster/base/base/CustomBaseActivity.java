package com.xq.customfaster.base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import com.xq.androidfaster.base.base.aspresenter.FasterBaseActivity;
import com.xq.androidfaster.util.EventManager;
import com.xq.androidfaster.util.tools.KeyboardUtils;
import com.xq.customfaster.util.event.ComponentEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CustomBaseActivity<T extends ICustomBaseView> extends FasterBaseActivity<T> implements ICustomBasePresenter<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);
        EventManager.regist(this);
    }

    @Override
    public void visible() {
        super.visible();
        KeyboardUtils.hideSoftInput(this);
    }

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
    public void isMyComponentEvent(ComponentEvent event){
        if (!event.getDestCommunicator().getComponentName().equalsIgnoreCase(getClass().getName()))
            return;
        else
            onComponentEvent(event);
    }

    //当接受到传递给本Activity的事件时回调的方法
    protected void onComponentEvent(ComponentEvent event) {

    }

}
