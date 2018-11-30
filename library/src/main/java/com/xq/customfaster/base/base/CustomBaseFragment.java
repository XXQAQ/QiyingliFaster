package com.xq.customfaster.base.base;

import android.os.Bundle;
import com.xq.androidfaster.base.base.FasterBaseFragment;
import com.xq.customfaster.util.EventManager;
import com.xq.customfaster.util.eventbus.ComponentEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CustomBaseFragment<T extends ICustomBaseView> extends FasterBaseFragment<T> implements ICustomBasePresenter<T> {

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);
        EventManager.regist(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unRegist(this);
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isMineComponentEvent(ComponentEvent event){
        if (!event.getDestCommunicator().getComponentName().equalsIgnoreCase(getClass().getName()))
            return;
        else
            onComponentEvent(event);
    }

    //当接受到传递给本Activity的事件时回调的方法
    protected void onComponentEvent(ComponentEvent event){

    }

}
