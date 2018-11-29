package com.xq.customfaster.base.basemedia;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;

public interface IAbsMediaPresenter<T extends IAbsView> extends IAbsPresenter<T> {

    public void getMedia(int flag);

    public void getMedia(int flag,int type,int max,boolean useCamera,boolean isCompress,int width,int height);

    public void getCamera(int flag);

    public void getCamera(int flag,int type,boolean isCompress);

    public void getFile(int flag,int max);

}
