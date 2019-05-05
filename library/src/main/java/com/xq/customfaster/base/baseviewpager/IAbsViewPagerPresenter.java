package com.xq.customfaster.base.baseviewpager;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.worldbean.bean.behavior.FragmentTitleBehavior;
import java.util.List;

public interface IAbsViewPagerPresenter<T extends IAbsViewPagerView> extends IAbsPresenter<T> {

    public List<FragmentTitleBehavior> getFragmentTitleList();

}