package com.xq.qiyinglifaster.base.base;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.xq.projectdefine.util.tools.KeyboardUtils;
import com.xq.qiyinglifaster.eventbus.FinishEvent;
import com.xq.qiyinglifaster.eventbus.FourComponentsEvent;
import com.xq.projectdefine.base.base.FasterBaseActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

public abstract class MyBaseActivity<T extends IMyBaseView> extends FasterBaseActivity<T> implements IMyBasePresenter<T> {

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishEvent(FinishEvent event){
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isMineFourCompoentsEvent(FourComponentsEvent event){
        if (!Arrays.asList(event.getComponentsName()).contains(getClass().getName()))
            return;
        else
            onFourCompoentsEvent(event);
    }

    //当接受到传递给本Actiity的事件时回调的方法
    protected abstract void onFourCompoentsEvent(FourComponentsEvent event);
}
