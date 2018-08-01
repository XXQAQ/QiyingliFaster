package com.xq.customfaster.base.baseviewpager;


import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.util.List;

public interface AbsViewPagerPresenter<T extends AbsViewPagerView> extends AbsPresenter<T> {

    //设置title与fragment集合（需要注意：Fragment附带在TitleBehavior的Tag中）
    public abstract List<TitleBehavior> getFragmentsAndTitles();

}