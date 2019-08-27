package com.xq.customfaster.util.callback.httpcallback;

import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xq.androidfaster.util.callback.FasterHttpCallback;
import java.io.File;

public abstract class CustomBaseFileCallback extends FileCallback implements FasterHttpCallback<File> {

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
    public void onStart(Request<File, ? extends Request> request) {
        super.onStart(request);
        requestStart(request);
    }

    @Deprecated
    @Override
    public void onSuccess(Response<File> response) {
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
    public void onError(Response<File> response) {
        super.onError(response);
        requestError(response);
    }

    @Override
    public boolean operating(File file, Object... objects) {
        return file != null;
    }

    @Override
    public void requestStart(Object... objects) {
        FasterHttpCallback.super.requestStart(objects);
    }

    @Override
    public void requestSuccess(File file, Object... objects) {
        FasterHttpCallback.super.requestSuccess(file,objects);
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
