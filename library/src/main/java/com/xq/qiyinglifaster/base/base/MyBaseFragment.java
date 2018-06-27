package com.xq.qiyinglifaster.base.base;


import android.os.Bundle;

import com.xq.qiyinglifaster.eventbus.FourComponentsEvent;
import com.xq.projectdefine.base.base.FasterBaseFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.Arrays;

public abstract class MyBaseFragment<T extends IMyBaseView> extends FasterBaseFragment<T> implements IMyBasePresenter<T> {

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isMineFourCompoentsEvent(FourComponentsEvent event){
        if (!Arrays.asList(event.getComponentsName()).contains(getClass().getName()))
            return;
        else
            onFourCompoentsEvent(event);
    }

    //当接受到传递给本Activity的事件时回调的方法
    protected void onFourCompoentsEvent(FourComponentsEvent event){

    }

}
