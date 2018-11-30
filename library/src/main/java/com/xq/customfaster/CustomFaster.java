package com.xq.customfaster;

import android.content.Context;
import android.widget.ImageView;

import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;



public class CustomFaster {

    public static void init(){
        Phoenix.config()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView, String imagePath, int type) {
                        com.xq.customfaster.util.ImageLoader.loadImage(mContext,imageView,imagePath);
                    }
                });
    }

}
