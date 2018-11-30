package com.xq.customfaster.util;

import com.xq.androidfaster.util.FasterEventManager;

import org.greenrobot.eventbus.EventBus;

public class EventManager extends FasterEventManager {

    private static Manager manager = new Manager() {
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
    };

    public static void regist(Object object,Object... objects) {
        manager.regist(object,objects);
    }

    public static void unRegist(Object object,Object... objects) {
        manager.unRegist(object,objects);
    }

}
