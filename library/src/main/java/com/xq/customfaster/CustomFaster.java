package com.xq.customfaster;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.xq.androidfaster.util.EventManager;
import com.xq.androidfaster.util.JsonConverter;

import org.greenrobot.eventbus.EventBus;

public class CustomFaster {

    public static void init(){

        com.xq.androidfaster.util.ImageLoader.setLoader(new com.xq.androidfaster.util.ImageLoader.Loader() {
            @Override
            public void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
                RequestOptions options = new RequestOptions();
                if (placeHolder != 0) options = options.placeholder(placeHolder);
                Glide.with(context).load(url).apply(options).into(view);
            }
        });

        EventManager.setManager(new EventManager.Manager() {
            @Override
            public void regist(Object object, Object... objects) {
                EventBus.getDefault().register(object);
            }

            @Override
            public void unRegist(Object object, Object... objects) {
                EventBus.getDefault().unregister(object);
            }

            @Override
            public void send(Object message, Object... objects) {
                EventBus.getDefault().post(message);
            }

            @Override
            public void cancle(Object message, Object... objects) {
                EventBus.getDefault().cancelEventDelivery(message);
            }
        });

        JsonConverter.setConverter(new JsonConverter.Converter() {
            @Override
            public <T> T jsonToObject(String json, Class<T> mClass, Object... objects) {
                return new Gson().fromJson(json,mClass);
            }

            @Override
            public String objectToJson(Object object, Object... objects) {
                return new Gson().toJson(object);
            }
        });

        Phoenix.config()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView, String imagePath, int type) {
                        com.xq.androidfaster.util.ImageLoader.loadImage(mContext,imageView,imagePath);
                    }
                });
    }

}
