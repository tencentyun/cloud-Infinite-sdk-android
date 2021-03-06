/*
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.tencent.qcloud.quic.test;

import java.util.UUID;

public class QuicNative {

    protected int handleId = UUID.randomUUID().toString().hashCode();

    protected static final int INIT = 0;
    protected static final int CONNECTED = 1;
    protected static final int RECEIVING = 2;
    protected static final int COMPLETED = 3;
    //已cancel_request
    protected static final int SERVER_FAILED = 4;
    //第一版，cancel_request, 以后版本 不cancel_request 满足复用
    protected static final int CLIENT_FAILED = 5;

    protected String host;
    protected String ip;
    protected int port;
    protected int tcpPort;
    protected boolean isCompleted = false;
    protected long idleStartTime = Long.MAX_VALUE;
    protected int currentState = INIT; //取值：init, connected, SERVER_FAILED

    static {
        System.loadLibrary("tnetquic");
        System.loadLibrary("costquic");
    }

    private NetworkCallback callback;

    public QuicNative(){}


    public void setCallback(NetworkCallback callback) {
        this.callback = callback;
    }

    @CallByNative
    private void onConnect(int code){
        currentState = CONNECTED;
        if(callback != null)callback.onConnect(handleId, code);
    }

    @CallByNative
    private void onDataReceive(byte[] data, int len){
        if(callback != null)callback.onDataReceive(handleId, data, len);
    }

    @CallByNative
    private void onCompleted(){
        if(callback != null)callback.onCompleted(handleId);
    }

    @CallByNative
    private void onClose(int code, String desc){
        currentState = SERVER_FAILED;
        if(callback != null)callback.onClose(handleId, code, desc);
    }


    public void connect(String host, String ip, int port, int tcpPort){
        connect(handleId, host, ip, port, tcpPort);
    }

    public void addHeader(String key, String value){
        addHeader(handleId, key, value);
    }

    public void sendRequest(byte[] content, int len, boolean finish){
        sendRequest(handleId, content, len, finish);
    }

    public void cancelRequest(){
        cancelRequest(handleId);
    } //取消本链接

    public String getState(){
        return getState(handleId);
    }


    public static native void init();

    public static native void setDebugLog(boolean isEnable);

    public static native void destory(); //取消所有链接

    private native void connect(int handleId, String host, String ip, int port, int tcpPort);

    private native void addHeader(int handleId, String key, String value);

    private native void sendRequest(int handleId, byte[] content, int len, boolean finish);

    private native void cancelRequest(int handleId);

    private native String getState(int handleId);


    public interface NetworkCallback {
        void onConnect(int handleId, int code);
        void onDataReceive(int handleId, byte[] data, int len);
        void onCompleted(int handleId);
        void onClose(int handleId, int code, String desc);
    }
}
