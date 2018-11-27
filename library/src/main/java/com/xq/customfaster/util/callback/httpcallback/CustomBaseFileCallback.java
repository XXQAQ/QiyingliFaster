package com.xq.customfaster.util.callback.httpcallback;

import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xq.androidfaster.util.callback.httpcallback.FasterHttpCallback;
import java.io.File;

public abstract class CustomBaseFileCallback extends FileCallback implements FasterHttpCallback<File> {

    @Override
    public void onStart(Request<File, ? extends Request> request) {
        super.onStart(request);
        requestStart(request);
    }

    private File file;
    @Override
    public void onSuccess(Response<File> response) {
        file = response.body();
        requestSuccess(response.body(),response);
    }

    @Deprecated
    @Override
    public void onFinish() {
        super.onFinish();
        requestFinish(file);
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

}
