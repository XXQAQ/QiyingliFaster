package com.xq.customfaster;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.xq.androidfaster.util.EventManager;
import com.xq.androidfaster.util.JsonConverter;
import org.greenrobot.eventbus.EventBus;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CustomFaster {

    public static void init(){

        com.xq.androidfaster.util.ImageLoader.setLoader(new com.xq.androidfaster.util.ImageLoader.Loader() {
            @Override
            public void loadImage(Context context, ImageView view, String url, int placeHolder, Object... objects) {
                RequestOptions options = new RequestOptions();
                if (placeHolder != 0) options = options.placeholder(placeHolder).diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(context).load(url).apply(options).into(view);
            }

            @Override
            public void loadImage(Context context, String url, com.xq.androidfaster.util.ImageLoader.BitmapTarget target, Object... objects) {
                SimpleTarget glideTarget = new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof BitmapDrawable)
                            target.onReceiveBitmap(((BitmapDrawable) resource).getBitmap());
                    }
                };
                RequestOptions options = new RequestOptions();
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(context)	.load(url).apply(options).into(glideTarget);
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
            public <T> T jsonToObject(String json, Class<T> mClass, Object... objects) throws RuntimeException {
                if (List.class.isAssignableFrom(mClass) && objects.length>=1 && objects[0]!= null)
                {
                    Class typeClass = (Class)objects[0];
                    Type type = new ParameterizedType() {
                        @Override
                        public Type[] getActualTypeArguments() {
                            return new Type[]{typeClass};
                        }

                        @Override
                        public Type getRawType() {
                            return List.class;
                        }

                        @Override
                        public Type getOwnerType() {
                            return null;
                        }
                    };
                    return new Gson().fromJson(json, type);
                }
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
