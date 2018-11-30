package com.xq.customfaster.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xq.androidfaster.util.FasterImageLoader;

public class ImageLoader extends FasterImageLoader {

    private static Loader loader = new Loader() {
        @Override
        public void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
            RequestOptions options = new RequestOptions();
            if (placeHolder != 0) options = options.placeholder(placeHolder);
            Glide.with(context).load(url).apply(options).into(view);
        }
    };

    public static void loadImage(Context context, ImageView view, String url, Object... objects) {
        loader.loadImage(context,view,url,objects);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
        loader.loadImage(context,view,url,placeHolder,objects);
    }

}
