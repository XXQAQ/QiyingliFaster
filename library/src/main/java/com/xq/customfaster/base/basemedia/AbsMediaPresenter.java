package com.xq.customfaster.base.basemedia;


import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsView;

import java.io.File;
import java.util.List;

public interface AbsMediaPresenter<T extends AbsView> extends AbsPresenter<T> {

    public void getPhotos(int what);

    public void getPhotos(int what, int number);

    public void getPhotos(int what, int number, int cutWidth, int cutHeight);

    public void getCamera(int what);

    public void getFile(int what);

}
