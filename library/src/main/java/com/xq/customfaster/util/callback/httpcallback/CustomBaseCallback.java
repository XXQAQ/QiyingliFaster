package com.xq.customfaster.util.callback.httpcallback;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xq.androidfaster.util.JsonConverter;
import com.xq.androidfaster.util.callback.httpcallback.FasterHttpCallback;

public abstract class CustomBaseCallback<T> extends AbsCallback<T> implements FasterHttpCallback<T> {

    protected Class<T> entityClass;

    public CustomBaseCallback(Class<T> entityClass) {
        this.entityClass = entityClass;
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

}
