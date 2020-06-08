package com.kbit.kbviewlib.jsbridge;

public interface JKEventHandler {
    public void send(String data);
    public void send(String data, CallBackFunction responseCallback);
}
