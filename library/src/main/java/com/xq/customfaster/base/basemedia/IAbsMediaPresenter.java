package com.xq.customfaster.base.basemedia;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.worldbean.bean.behavior.SizeBehavior;

public interface IAbsMediaPresenter<T extends IAbsView> extends IAbsPresenter<T> {

    public static final int TYPE_PHOTO = 0x01;
    public static final int TYPE_VIDEO = 0x02;

    public void getMedia(int flag);

    public void getMedia(int flag, int type, int max, boolean useCamera);

    public void getMedia(int flag, int type, int max, boolean useCamera, boolean isCompress, SizeBehavior cropSize);

    public void getCamera(int flag);

    public void getCamera(int flag,int type);

    public void getCamera(int flag,int type,boolean isCompress,SizeBehavior cropSize);

    public void getFile(int flag);

    public void getFile(int flag,int max);

}
