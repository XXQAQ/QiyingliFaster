package com.xq.qiyinglifaster.eventbus;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xq on 2017/4/20 0020.
 */

//4大组件通用通信方式（包括fragemnt，adapter均可使用）
public class FourComponentsEvent implements Parcelable {

    private String[] componentsName;
    private int control;
    private Bundle bundle;

    public FourComponentsEvent(String[] componentsName, int control, Bundle bundle) {
        this.componentsName = componentsName;
        this.control = control;
        this.bundle = bundle;
    }

    public String[] getComponentsName() {
        return componentsName;
    }

    public void setComponentsName(String[] componentsName) {
        this.componentsName = componentsName;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.componentsName);
        dest.writeInt(this.control);
        dest.writeBundle(this.bundle);
    }

    protected FourComponentsEvent(Parcel in) {
        this.componentsName = in.createStringArray();
        this.control = in.readInt();
        this.bundle = in.readBundle();
    }

    public static final Creator<FourComponentsEvent> CREATOR = new Creator<FourComponentsEvent>() {
        @Override
        public FourComponentsEvent createFromParcel(Parcel source) {
            return new FourComponentsEvent(source);
        }

        @Override
        public FourComponentsEvent[] newArray(int size) {
            return new FourComponentsEvent[size];
        }
    };
}
