package com.example.ruler.noteapplication.request.listener;

public class DisposeDataHandle {
    public DisposeDataListener listener=null;
    public Class<?>aClass=null;
    public String source;

    public DisposeDataHandle(DisposeDataListener listener){
        this.listener=listener;
    }

    public DisposeDataHandle(DisposeDataListener listener,Class<?>aClass){
        this.listener=listener;
        this.aClass=aClass;
    }
}
