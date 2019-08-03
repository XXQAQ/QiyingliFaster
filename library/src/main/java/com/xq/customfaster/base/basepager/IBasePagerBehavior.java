package com.xq.customfaster.base.basepager;

import com.xq.androidfaster.base.core.Controler;
import com.xq.worldbean.bean.behavior.FragmentBehavior;

import java.util.List;

public interface IBasePagerBehavior extends Controler {

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    //获取Fragment封装对象的集合
    public List<FragmentBehavior> getFragmentBehaviorList();



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    //刷新ViewPage
    public void refreshPager();

    //提供给P层用于初始化
    public void initPager(List list);

}