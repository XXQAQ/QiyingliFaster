package com.xq.customfaster.base.basemedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.xq.androidfaster.FasterInterface;
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.util.callback.UniverseCallback;
import com.xq.androidfaster.util.constant.PermissionConstants;
import com.xq.androidfaster.util.tools.PermissionUtils;
import com.xq.androidfaster.util.tools.UriUtils;
import com.xq.customfaster.R;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public interface IBaseMediaPresenter<T extends IAbsView> extends IAbsMediaPresenter<T> {

    @Override
    default void getMedia(int flag){
        getMediaDelegate().getMedia(flag);
    }

    @Override
    default void getMedia(int flag, int type, int max, boolean useCamera, boolean isCompress, int width, int height) {
        getMediaDelegate().getMedia(flag,type,max,useCamera,isCompress,width,height);
    }

    @Override
    default void getCamera(int flag) {
        getMediaDelegate().getCamera(flag);
    }

    @Override
    default void getCamera(int flag, int type, boolean isCompress) {
        getMediaDelegate().getCamera(flag,type,isCompress);
    }

    @Override
    default void getFile(int flag, int max) {
        getMediaDelegate().getFile(flag,max);
    }

    public MediaDelegate getMediaDelegate();

    public abstract class MediaDelegate<T extends IAbsView> extends AbsPresenterDelegate<T> implements IAbsMediaPresenter<T> {

        public static final int TYPE_PHOTO = 0x1;
        public static final int TYPE_VIDEO = 0x10;
        public static final int TYPE_AUDIO = 0x100;

        public static final int REQUEST_CODE_MEDIA = 1;
        public static final int REQUEST_CODE_CAMERA= 2;
        public static final int REQUEST_CODE_FILE= 3;

        protected int flag;

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

            if (resultCode != RESULT_OK)
                return;

            if (requestCode == REQUEST_CODE_MEDIA)
            {
                //返回的数据
                List<MediaEntity> list = Phoenix.result(data);

                onReceiveMedia(mediaEntityListToFileList(list),flag);
            }
            else    if(requestCode == REQUEST_CODE_CAMERA)
            {

            }
            else    if(requestCode == REQUEST_CODE_FILE)
            {
                Uri uri = data.getData();            //得到uri
                if (uri == null)
                    return;

                onReceiveFile(Arrays.asList(new File[]{new File(UriUtils.getFileForUri(uri))}), flag);
            }
        }

        @Override
        public void getMedia(int flag) {
            getMedia(flag,TYPE_PHOTO|TYPE_VIDEO,0,true,true,0,0);
        }

        @Override
        public void getMedia(int flag, int type, int max, boolean useCamera, boolean isCompress, int width, int height) {
            this.flag = flag;

            String[] permissions;
            if (!useCamera)
                permissions = new String[]{PermissionConstants.STORAGE};
            else
                permissions = new String[]{PermissionConstants.STORAGE,PermissionConstants.CAMERA};

            checkPermission(new UniverseCallback() {
                @Override
                public void onCallback(Object... objects) {
                    int mimeType = MimeType.ofAll();
                    PhoenixOption option = Phoenix.with()
                            .theme(getColor(R.color.colorPrimary))// 主题
                            .fileType(mimeType)//显示的文件类型图片、视频、图片和视频
                            .maxPickNumber(max)// 最大选择数量
                            .minPickNumber(0)// 最小选择数量
                            .spanCount(4)// 每行显示个数
                            .enablePreview(true)// 是否开启预览
                            .enableCamera(useCamera)// 是否开启拍照
                            .enableAnimation(true)// 选择界面图片点击效果
                            .enableCompress(isCompress)// 是否开启压缩
                            .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                            .compressVideoFilterSize(1024)//多少kb以下的视频不压缩
                            .thumbnailHeight(height)// 选择界面图片高度
                            .thumbnailWidth(width)// 选择界面图片宽度
                            .enableClickSound(false)// 是否开启点击声音
                            .videoFilterTime(0)//显示多少秒以内的视频
                            .mediaFilterSize(0);//显示多少kb以下的图片/视频，默认为0，表示不限制;
                    //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                    if (getAreActivity() != null)
                        option.start(getAreActivity(),PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_MEDIA);
                    else    if (getAreFragment() != null)
                        option.start(getAreFragment(),PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_MEDIA);
                }
            },permissions);
        }

        @Override
        public void getCamera(int flag) {
            getCamera(flag,TYPE_PHOTO|TYPE_VIDEO,true);
        }

        @Override
        public void getCamera(int flag, int type, boolean isCompress) {
            this.flag = flag;

            checkPermission(new UniverseCallback() {
                @Override
                public void onCallback(Object... objects) {

                }
            },PermissionConstants.STORAGE,PermissionConstants.CAMERA);
        }

        @Override
        public void getFile(int flag, int max) {
            this.flag = flag;

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

        private List<File> mediaEntityListToFileList(List<MediaEntity> list){
            List<File> list_file = new LinkedList<>();
            for (MediaEntity entity:list)
            {
                list_file.add(new File(entity.getFinalPath()));
            }
            return list_file;
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

        //接收到多媒体文件后调用
        protected abstract void onReceiveMedia(List<File> list, int flag);

        //接收到一个相机文件后调用
        protected abstract void onReceiveCamera(File file, int flag);

        //接收到一个文件后调用
        protected abstract void onReceiveFile(List<File> list, int flag);

    }
}