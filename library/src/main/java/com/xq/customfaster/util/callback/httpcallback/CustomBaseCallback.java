package com.xq.customfaster.util.callback.httpcallback;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xq.androidfaster.util.FasterHttpCallback;
import com.xq.worldbean.util.JsonConverter;

public abstract class CustomBaseCallback<T> extends AbsCallback<T> implements FasterHttpCallback<T> {

    protected Class<T> entityClass;

    public CustomBaseCallback(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Deprecated
    @Override
    public void uploadProgress(Progress progress) {
        super.uploadProgress(progress);
        if (progress != null) upLoadProgress(progress.fraction);
    }

    @Deprecated
    @Override
    public void downloadProgress(Progress progress) {
        super.downloadProgress(progress);
        if (progress != null) downLoadProgress(progress.fraction);
    }

    @Deprecated
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        requestStart(request);
    }

    @Deprecated
    @Override
    public void onCacheSuccess(Response<T> response) {
        super.onCacheSuccess(response);
        onSuccess(response);
    }

    @Deprecated
    @Override
    public void onSuccess(Response<T> response) {
        requestSuccess(response.body(),response);
    }

    @Deprecated
    @Override
    public void onFinish() {
        super.onFinish();
        requestFinish();
    }

    @Deprecated
    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        requestError(response);
    }

    @Deprecated
    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        if (entityClass.isAssignableFrom(byte[].class))
            return (T) response.body().bytes();
        else    if (entityClass.isAssignableFrom(String.class))
            return (T) response.body().string();
        else
            return JsonConverter.jsonToObject(response.body().string(),entityClass);
    }

    @Override
    public void requestStart(Object... objects) {
        FasterHttpCallback.super.requestStart(objects);
    }

    @Override
    public void requestSuccess(T t, Object... objects) {
        FasterHttpCallback.super.requestSuccess(t,objects);
    }

    @Override
    public void requestError(Object... objects) {
        FasterHttpCallback.super.requestError(objects);
    }

    @Override
    public void requestFinish(Object... objects) {
        FasterHttpCallback.super.requestFinish(objects);
    }
}
