package com.xq.customfaster.callback.httpcallback;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.Request;
import com.xq.projectdefine.callback.httpcallback.FasterBaseCallback;


public abstract class MyBaseCallback<T> extends AbsCallback<T> implements FasterBaseCallback<T> {

    protected Class<T> entityClass;

    public MyBaseCallback(Class<T> entityClass) {
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
        requestCacheSuccess(response.body(),response);
    }

    @Override
    public void onSuccess(Response<T> response) {
        requestSuccess(response.body(),response);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        requestFinish();
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        requestError(response);
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return convertJson(response.body().string());
    }

    //公共部分
    @Override
    public void requestStart(Object... objects) {

    }

    @Override
    public void requestCacheSuccess(T t, Object... objects) {
        requestSuccess(t,objects);
    }

    @Override
    public void requestError(Object... objects) {

    }

    @Override
    public void requestFinish(Object... objects) {

    }

    @Override
    public T convertJson(String data) throws Throwable {
        return new Gson().fromJson(data, entityClass);
    }
}
