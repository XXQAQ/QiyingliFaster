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

    //接收到图片后调用
    public abstract void onReceivePhotos(List<File> list_file, int what);

    //接收到一个录像后调用
    public abstract void onReceiveCamera(File file, int what);

    //接收到一个文件后调用
    public abstract void onReceiveFile(File file, int what);

}
