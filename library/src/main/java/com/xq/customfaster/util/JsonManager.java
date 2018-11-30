package com.xq.customfaster.util;

import com.google.gson.Gson;
import com.xq.androidfaster.util.FasterJsonManager;

public class JsonManager extends FasterJsonManager{

    private static Manager manager = new Manager() {
        @Override
        public <T> T jsonToObject(String json, Class<T> mClass, Object... objects) {
            return new Gson().fromJson(json,mClass);
        }

        @Override
        public String objectToJson(Object object, Object... objects) {
            return new Gson().toJson(object);
        }
    };

    public static <T> T jsonToObject(String json, Class<T> mClass, Object... objects) {
        return manager.jsonToObject(json,mClass,objects);
    }

    public static String objectToJson(Object object, Object... objects) {
        return manager.objectToJson(object,objects);
    }

}
