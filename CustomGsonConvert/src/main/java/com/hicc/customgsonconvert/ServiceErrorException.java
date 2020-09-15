package com.hicc.customgsonconvert;

import java.io.IOException;

/**
 * Created by cooldog on 2020/9/15.
 * desc:
 */
public class ServiceErrorException extends IOException {
    public int errorCode;

    public ServiceErrorException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public ServiceErrorException(Exception e) {
        super(e);
    }
}
