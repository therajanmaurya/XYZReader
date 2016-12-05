package com.opensource.xyz.reader.util;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class MyGsonTypeAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new AutoValueGson_MyGsonTypeAdapterFactory();
    }
}
