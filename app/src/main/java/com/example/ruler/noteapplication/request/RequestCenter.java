package com.example.ruler.noteapplication.request;


import com.example.ruler.noteapplication.Data.BasePatch;
import com.example.ruler.noteapplication.request.listener.DisposeDataHandle;
import com.example.ruler.noteapplication.request.listener.DisposeDataListener;

public class RequestCenter {

    public static void postRequest(String urlUpdatePatch, String url, DisposeDataListener listener, Class<BasePatch> basePatchClass){
        CommonOkhttpClient.get(CommonRequest.createGetRequest(url, null),new DisposeDataHandle(listener,basePatchClass));
    }

    public static void requestRecommandData(DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstants.ROOT_URL+HttpConstants.HOME_RECOMMAND,null,listener,BasePatch.class);
    }

    public static void requestPatchUpdateInfo(DisposeDataListener disposeDataListener) {
        RequestCenter.postRequest(HttpConstants.URL_UPDATE_PATCH,null,disposeDataListener,BasePatch.class);
    }
}
