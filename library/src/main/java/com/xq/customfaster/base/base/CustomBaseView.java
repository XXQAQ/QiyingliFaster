package com.xq.customfaster.base.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import com.xq.androidfaster.base.base.FasterBaseAnother;
import com.xq.customfaster.widget.view.CustomRefreshLoadView;

public abstract class CustomBaseView<T extends ICustomBaseBehavior> extends FasterBaseAnother<T> implements ICustomBaseBehavior<T> {

    public CustomBaseView(T another) {
        super(another);
    }

    @Override
    public void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);

        if (getLayoutId() != 0)
            initCommonView(this);
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
}
