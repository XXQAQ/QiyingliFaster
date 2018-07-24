package com.xq.customfaster.callback.httpcallback;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xq.projectdefine.callback.httpcallback.FasterBaseCallback;


public abstract class CustomBaseCallback<T> extends AbsCallback<T> implements FasterBaseCallback<T> {

    protected Class<T> entityClass;

    private T t;

    public CustomBaseCallback(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        requestStart(request);
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
        super.onCacheSuccess(response);
        onSuccess(response);
    }

    @Override
    public void onSuccess(Response<T> response) {
        t = response.body();
        requestSuccess(t,response);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        requestFinish(t);
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        requestError(response);
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return new Gson().fromJson(response.body().string(), entityClass);
    }

}
