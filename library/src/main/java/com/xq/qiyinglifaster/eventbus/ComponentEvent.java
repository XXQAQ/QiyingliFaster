package com.xq.qiyinglifaster.eventbus;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


//组件通用通信方式
public class ComponentEvent implements Parcelable {

    private Communicator destCommunicator;
    private Communicator srcCommunicator;
    private Bundle bundle;

    public ComponentEvent(String componentName, int control, Bundle bundle){
        this(new Communicator(componentName,control),bundle);
    }

    public ComponentEvent(Communicator destCommunicator, Bundle bundle) {
        this.destCommunicator = destCommunicator;
        this.bundle = bundle;
    }

    public ComponentEvent(Communicator destCommunicator, Communicator srcCommunicator, Bundle bundle) {
        this.destCommunicator = destCommunicator;
        this.srcCommunicator = srcCommunicator;
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public ComponentEvent setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public Communicator getDestCommunicator() {
        return destCommunicator;
    }

    public ComponentEvent setDestCommunicator(Communicator destCommunicator) {
        this.destCommunicator = destCommunicator;
        return this;
    }

    public Communicator getSrcCommunicator() {
        return srcCommunicator;
    }

    public ComponentEvent setSrcCommunicator(Communicator srcCommunicator) {
        this.srcCommunicator = srcCommunicator;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.destCommunicator, flags);
        dest.writeParcelable(this.srcCommunicator, flags);
        dest.writeBundle(this.bundle);
    }

    protected ComponentEvent(Parcel in) {
        this.destCommunicator = in.readParcelable(Communicator.class.getClassLoader());
        this.srcCommunicator = in.readParcelable(Communicator.class.getClassLoader());
        this.bundle = in.readBundle();
    }

    public static final Creator<ComponentEvent> CREATOR = new Creator<ComponentEvent>() {
        @Override
        public ComponentEvent createFromParcel(Parcel source) {
            return new ComponentEvent(source);
        }

        @Override
        public ComponentEvent[] newArray(int size) {
            return new ComponentEvent[size];
        }
    };

    public static class Communicator implements Parcelable{

        private String componentName;
        private int control;

        public Communicator(String componentName, int control) {
            this.componentName = componentName;
            this.control = control;
        }

        public String getComponentName() {
            return componentName;
        }

        public Communicator setComponentName(String componentName) {
            this.componentName = componentName;
            return this;
        }

        public int getControl() {
            return control;
        }

        public Communicator setControl(int control) {
            this.control = control;
            return this;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.componentName);
            dest.writeInt(this.control);
        }

        public Communicator() {
        }

        protected Communicator(Parcel in) {
            this.componentName = in.readString();
            this.control = in.readInt();
        }

        public static final Creator<Communicator> CREATOR = new Creator<Communicator>() {
            @Override
            public Communicator createFromParcel(Parcel source) {
                return new Communicator(source);
            }

            @Override
            public Communicator[] newArray(int size) {
                return new Communicator[size];
            }
        };
    }

}
