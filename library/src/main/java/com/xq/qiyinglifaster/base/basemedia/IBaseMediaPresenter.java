package com.xq.qiyinglifaster.base.basemedia;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.xq.projectdefine.util.tools.BundleUtil;
import com.xq.projectdefine.util.tools.FileUtils;
import com.xq.projectdefine.util.tools.UriToPathUtils;
import com.xq.qiyinglifaster.R;
import com.xq.qiyinglifaster.util.SDUtils;
import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.base.basemedia.IFasterBaseMediaPresenter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;


/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IBaseMediaPresenter<T extends AbsView> extends IFasterBaseMediaPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseMediaPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseMediaPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseMediaPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseMediaPresenter.super.onDestroy();
    }

    default void onActivityResult(int requestCode, int resultCode, Intent data) {

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
                        try
                        {
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
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onReceivePhotos(list_file, getMediaBuilder().what);
                        }
                    });
                }
            }).start();
        }
        else    if(requestCode == REQUEST_CODE_CUT)
        {
            Bundle bundle = data.getExtras();

            if (bundle == null)
                return;

            Bitmap bitmap = bundle.getParcelable(BundleUtil.KEY_DATA);

            try
            {
                String path_cache = SDUtils.getCachePhoto(getContext());

                bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(path_cache));

                Luban.with(getContext())
                        .load(new File(path_cache))
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }
                            @Override
                            public void onSuccess(File file) {
                                List<File> list = new LinkedList<>();
                                list.add(file);
                                onReceivePhotos(list,getMediaBuilder().what);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }
        else    if(requestCode == REQUEST_CODE_CAMERA)
        {

        }
        else    if(requestCode == REQUEST_CODE_FILE)
        {
            Uri uri = data.getData();            //得到uri

            if (uri == null)
                return;

            onReceiveFile(new File(UriToPathUtils.getFileAbsolutePath(getContext(), uri)),getMediaBuilder().what);
        }
    }

    default void getPhotos(int what, int number, int width, int height) {
        getMediaBuilder().what = what;
        getMediaBuilder().width = width;
        getMediaBuilder().height = height;

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

    default void getCamera(int what) {
        //TODO:摄像
    }

    //剪裁的方法
    default void curImage(Uri uri) {

        getMediaBuilder().width = 0;
        getMediaBuilder().height = 0;

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setDataAndType(uri,"image/*");

        intent.putExtra("crop","true");

        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);

        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);

        intent.putExtra("return-data",true);

        ((Activity)getContext()).startActivityForResult(intent,REQUEST_CODE_CUT);
    }
}