package com.example.ruler.noteapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.ruler.noteapplication.request.RequestCenter;
import com.example.ruler.noteapplication.request.listener.DisposeDataListener;

import java.io.File;

public class AndFixService extends Service{
    private String TAG="AndFix";
    private String downLoadPath;
    private String downLoadFile;
    private static final String FIND_INCLUE=".apatch";
    private static final int PATCH_UPDATE=0x02;
    private static final int PATCH_DOWNLOAD=0x01;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PATCH_DOWNLOAD:
                    break;
                case PATCH_UPDATE:
                    checkUpdate();
                    break;
            }
        }
    };

    //检查服务器有没有补丁文件（.apatch）
    private void checkUpdate() {
        RequestCenter.requestPatchUpdateInfo(new DisposeDataListener(){
            @Override
            public void onSucess(Object responseObj) {

            }

            @Override
            public void onFailure(Object responseObj) {

            }

            @Override
            public void onProgress(int obj) {

            }
        });
    }

    public AndFixService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessage(PATCH_UPDATE);

        return START_NOT_STICKY;
    }

    public void init(){
        downLoadPath=getExternalCacheDir().getAbsolutePath()+"/MyPatch/";//测试路径未生成
        File patchPath=new File(downLoadPath);

        try {
            if (patchPath==null||!patchPath.exists()){
                patchPath.mkdir();
            }
        }catch (Exception e){
            e.printStackTrace();
            stopSelf();
        }
    }


    public void downLoadFile(){

    }
}
