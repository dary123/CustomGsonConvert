package com.hicc.customgsonconvert;

import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by cooldog on 2020/9/15.
 * desc:
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private ServerErrorHandleListener listener;


    GsonResponseBodyConverter(ServerErrorHandleListener listener, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        this.listener = listener;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        try {
            JSONObject object = new JSONObject(json);
            IOException serverException = listener.needThrow(object,json);
            if (serverException == null) {
                return adapter.fromJson(json);
            } else {
                throw serverException;
            }
        } catch (JSONException e) {
            throw new ServiceErrorException(e);
        } finally {
            value.close();
        }
    }

}
