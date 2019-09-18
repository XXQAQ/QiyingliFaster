package com.xq.customfaster.base.base;

import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import com.xq.androidfaster.base.base.FasterBaseAnother;
import com.xq.customfaster.widget.view.CustomRefreshLoadView;

public abstract class CustomBasePresenter<T extends ICustomBaseBehavior> extends FasterBaseAnother<T> implements ICustomBaseBehavior<T> {

    public CustomBasePresenter(T another) {
        super(another);
    }

    @Override
    public Toolbar getToolbar() {
        return getBindAnother().getToolbar();
    }

    @Override
    public ViewGroup getBarLayout() {
        return getBindAnother().getBarLayout();
    }

    @Override
    public CustomRefreshLoadView getRefreshLoadView() {
        return getBindAnother().getRefreshLoadView();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

}
