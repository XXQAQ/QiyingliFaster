package com.xq.customfaster.base.base;


import com.xq.projectdefine.base.base.IFasterBaseView;

public interface ICustomBaseView<T extends ICustomBasePresenter> extends IFasterBaseView<T> {

    //手动调用以设置Toolbar标题
    public void initToolbar(String title);

    //手动调用以设置Toolbar标题+图标
    public void initToolbar(String title, boolean isShowIcon);

    //页面行为描述
    public String getBehaviorDescription();

}
