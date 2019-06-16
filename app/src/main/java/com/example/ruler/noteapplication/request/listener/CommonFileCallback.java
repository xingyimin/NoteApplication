package com.example.ruler.noteapplication.request.listener;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonFileCallback implements Callback{
    protected final String RESULT_CODE="name";
    protected final int RESULT_CODE_vALUE=0;
    protected final String ERROR_MSG="emsg";
    protected final String EMPTY_MSG="";
    private final int PROGRESS_MESSAGE=0x01;


    protected final int NETWORK_ERROR=-1;
    protected final int JSON_ERROR=-2;
    protected final int OTHER_ERROR=-3;

    private Handler handler;
    private DisposeDataListener listener;
    private String filePath;

    public CommonFileCallback(DisposeDataHandle handle){
        this.listener=(DisposeDataListener)handle.listener;
        this.filePath=handle.source;
        this.handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case PROGRESS_MESSAGE:
                        listener.onProgress((int)msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
