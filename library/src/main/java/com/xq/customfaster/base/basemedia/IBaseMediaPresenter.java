package com.xq.customfaster.base.basemedia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.xq.androidfaster.FasterInterface;
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.util.callback.UniverseCallback;
import com.xq.androidfaster.util.constant.PermissionConstants;
import com.xq.androidfaster.util.tools.PermissionUtils;
import com.xq.androidfaster.util.tools.UriUtils;
import com.xq.customfaster.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import top.zibin.luban.Luban;
import top.zibin.luban.OnRenameListener;

public interface IBaseMediaPresenter<T extends IAbsView> extends IAbsMediaPresenter<T> {

    @Override
    default void getPhotos(int what){
        getMediaDelegate().getPhotos(what);
    }

    @Override
    default void getPhotos(int what, int number){
        getMediaDelegate().getPhotos(what,number);
    }

    @Override
    default void getPhotos(int what, int number, int cutWidth, int cutHeight) {
        getMediaDelegate().getPhotos(what,number,cutWidth,cutHeight);
    }

    @Override
    default void getCamera(int what) {
        getMediaDelegate().getCamera(what);
    }

    @Override
    default void getFile(int what) {
        getMediaDelegate().getFile(what);
    }

    public MediaDelegate getMediaDelegate();

    public abstract class MediaDelegate<T extends IAbsView> extends AbsPresenterDelegate<T> implements IAbsMediaPresenter<T> {

        public static final int REQUEST_CODE_PHOTOS = 1;
        public static final int REQUEST_CODE_CAMERA= 2;
        public static final int REQUEST_CODE_FILE= 3;

        protected int what;

        public MediaDelegate(IAbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);

            if (data == null)
                return;

            if (requestCode == REQUEST_CODE_PHOTOS)
            {
                final List<String> list_selected = Matisse.obtainPathResult(data);

                final List<File> list_file = new LinkedList<>();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (String path : list_selected)
                        {
                            try {
                                File file = Luban
                                        .with(getContext())
                                        .load(new File(path))
                                        .setRenameListener(new OnRenameListener() {
                                            @Override
                                            public String rename(String filePath) {
                                                return new File(filePath).getName();
                                            }
                                        })
                                        .get()
                                        .get(0);
                                list_file.add(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onReceivePhotos(list_file, what);
                            }
                        });
                    }
                }).start();
            }
            else    if(requestCode == REQUEST_CODE_CAMERA)
            {

            }
            else    if(requestCode == REQUEST_CODE_FILE)
            {
                Uri uri = data.getData();            //得到uri

                if (uri == null)
                    return;

                onReceiveFile(new File(UriUtils.getFileForUri(uri)),what);
            }
        }

        @Override
        public void getPhotos(int what){
            getPhotos(0,1);
        }

        @Override
        public void getPhotos(int what, int number){
            getPhotos(what,number,0,0);
        }

        @Override
        public void getPhotos(int what, int number, int cutWidth, int cutHeight) {
            this.what = what;

            checkPermission(new UniverseCallback() {
                @Override
                public void onCallback(Object... objects) {

                    Matisse matisse = null;

                    if (getAreActivity() != null)
                        matisse = Matisse.from(getAreActivity());
                    else    if (getAreFragment() != null)
                        matisse = Matisse.from(getAreFragment());

                    matisse.choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(number)
                            .gridExpectedSize(getContext().getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.65f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_PHOTOS);
                }
            }, PermissionConstants.STORAGE);
        }

        @Override
        public void getCamera(int what) {
            this.what = what;

            checkPermission(new UniverseCallback() {
                @Override
                public void onCallback(Object... objects) {

                }
            },PermissionConstants.STORAGE,PermissionConstants.CAMERA);
        }

        @Override
        public void getFile(int what) {
            this.what = what;

            checkPermission(new UniverseCallback() {
                @Override
                public void onCallback(Object... objects) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    if (getAreActivity() != null)
                        getAreActivity().startActivityForResult(intent, REQUEST_CODE_FILE);
                    else if (getAreFragment() != null)
                        getAreFragment().startActivityForResult(intent, REQUEST_CODE_FILE);

                }
            },PermissionConstants.STORAGE);

        }

        protected void checkPermission(UniverseCallback callback, String...  permission){
            if (FasterInterface.isIsAutoPermission())
            {
                PermissionUtils.permission(permission)
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                callback.onCallback();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                            }
                        })
                        .request();
            }
            else
            {
                callback.onCallback();
            }
        }

        //接收到图片后调用
        protected abstract void onReceivePhotos(List<File> list_file, int what);

        //接收到一个录像后调用
        protected abstract void onReceiveCamera(File file, int what);

        //接收到一个文件后调用
        protected abstract void onReceiveFile(File file, int what);

    }
}