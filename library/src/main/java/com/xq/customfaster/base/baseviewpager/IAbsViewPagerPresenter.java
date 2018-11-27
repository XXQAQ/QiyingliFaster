package com.xq.customfaster.base.baseviewpager;


import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.bean.behavior.TitleBehavior;
import java.util.List;

public interface IAbsViewPagerPresenter<T extends IAbsViewPagerView> extends IAbsPresenter<T> {

    //设置title与fragment集合（需要注意：Fragment附带在TitleBehavior的Tag中）
    public abstract List<TitleBehavior> getFragmentsAndTitles();

}