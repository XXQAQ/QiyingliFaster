package com.xq.customfaster.base.basemedia;

import android.util.Size;
import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;

public interface IAbsMediaPresenter<T extends IAbsView> extends IAbsPresenter<T> {

    public void getMedia(int flag);

    public void getMedia(int flag, int type, int max, boolean useCamera);

    public void getMedia(int flag, int type, int max, boolean useCamera, boolean isCompress, Size cropSize);

    public void getCamera(int flag);

    public void getCamera(int flag,int type);

    public void getCamera(int flag,int type,boolean isCompress,Size cropSize);

    public void getFile(int flag);

    public void getFile(int flag,int max);

}
