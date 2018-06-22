package com.xq.qiyinglifaster.util;


import android.content.Context;
import android.os.Environment;
import com.xq.projectdefine.util.tools.FileUtils;
import java.io.File;
import java.util.Date;

/**
 * Created by xq on 2017/4/11 0011.
 */

//本类专用于存储各种静态变量（可变）
public class SDUtils {

    //获取Android/data/包名下的缓存文件夹
    public static String getCacheDir(Context context){
        String path = getSDPath()+ File.separator+"Android"+File.separator+"data"+File.separator+context.getPackageName()+File.separator + "data";
        FileUtils.createOrExistsDir(path);
        return path;
    }

    //获取/data/data/包名下的存储文件夹
    public static String getDir(Context context){
        String path = context.getFilesDir().getAbsolutePath()+File.separator + "data";
        FileUtils.createOrExistsDir(path);
        return path;
    }

    //获取/data/data/runtime下的存储文件夹（该文件专注于缓存运行时数据，防止APP生命周期异常）
    public static String getDirRuntimeData(Context context){
        return getDir(context) + File.separator + "runtime";
    }

    //获取摄像或拍照生成的临时文件名
    public static String getCachePhoto(Context context){
        return getCacheDir(context)+File.separator+"Camera_"+new Date().getTime()+".jpg";
    }

    //获取摄像或拍照生成的临时文件名(文件名不变)
    public static String getCachePhoto(Context context,String fileName){
        return getCacheDir(context)+File.separator+fileName;
    }

    //获取SD卡根路径
    public static String getSDPath(){
        if(isMounted())
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        return null;
    }

    //SD卡是否已挂载
    private static boolean isMounted(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


}
