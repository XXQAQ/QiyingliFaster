package com.xq.customfaster.base.baseviewpager;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.util.List;


public interface IBaseViewPagerPresenter<T extends IBaseViewPagerView> extends AbsPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    default void onResume() {

    }

    @Override
    default void onPause() {

    }

    @Override
    default void onDestroy() {

    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //设置title与fragment集合（需要注意：Fragment附带在TitleBehavior的Tag中）
    public abstract List<TitleBehavior> getFragmentsAndTitles();

}