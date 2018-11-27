package com.xq.customfaster.base.basemedia;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;

public interface IAbsMediaPresenter<T extends IAbsView> extends IAbsPresenter<T> {

    public void getPhotos(int what);

    public void getPhotos(int what, int number);

    public void getPhotos(int what, int number, int cutWidth, int cutHeight);

    public void getCamera(int what);

    public void getFile(int what);

}
