package com.ingjcfv.rendicash11.utils;

public interface CallbackResultado<T> {
    void onSuccess(T result);
    void onError(Exception e);
}
