package com.hicc.customgsonconvert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by cooldog on 2020/9/15.
 * desc:
 */
public final class MyGsonConvertFactory extends Converter.Factory {
    private ServerErrorHandleListener listener;

    public static MyGsonConvertFactory create() {
        return create( null, new Gson());
    }

    public static MyGsonConvertFactory create(ServerErrorHandleListener listener) {
        return create(listener, new Gson());
    }

    public static MyGsonConvertFactory create(ServerErrorHandleListener listener, Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        if (listener == null) {
            listener = new ServerErrorHandleListener() {
                @Override
                public IOException needThrow(JSONObject jsonObject, String body) throws JSONException {
                    return null;
                }
            };
        }
        return new MyGsonConvertFactory(listener, gson);
    }


    private final Gson gson;

    private MyGsonConvertFactory(ServerErrorHandleListener listener, Gson gson) {
        this.gson = gson;
        this.listener = listener;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type == RequestBody.class) {
            return null;
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(listener, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }
}
