package com.xq.customfaster.base.basepager;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.worldbean.bean.behavior.FragmentBehavior;
import java.util.List;

public interface IAbsPagerPresenter<T extends IAbsPagerView> extends IAbsPresenter<T> {

    public List<FragmentBehavior> getFragmentBehaviorList();

}