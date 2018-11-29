package com.xq.customfaster;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.xq.androidfaster.FasterInterface;
import com.xq.androidfaster.util.callback.UniverseCallback;

public class CustomFasterInterface {

    static {
        FasterInterface.addInitCallback(new UniverseCallback() {
            @Override
            public void onCallback(Object... objects) {
                Phoenix.config()
                        .imageLoader(new ImageLoader() {
                            @Override
                            public void loadImage(Context mContext, ImageView imageView, String imagePath, int type) {
                                Glide.with(mContext)
                                        .load(imagePath)
                                        .into(imageView);
                            }
                        });
            }
        });
    }

}
