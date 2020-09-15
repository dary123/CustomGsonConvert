package com.hicc.customgsonconvert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by cooldog on 2020/9/15.
 * desc:
 */
public interface ServerErrorHandleListener {
    /**
     * 自定义抛出服务器定义错误
     * @param jsonObject json
     * @param body       body
     * @return 自定义异常抛出
     * @throws JSONException
     */
    IOException needThrow(JSONObject jsonObject, String body) throws JSONException;

}
