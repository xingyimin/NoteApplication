package com.example.ruler.noteapplication.request.listener;

import android.os.Handler;
import android.os.Looper;

import com.example.ruler.noteapplication.request.error.OkHttpException;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonJsonCallback implements Callback {
    protected final String RESULT_CODE="name";
    protected final int RESULT_CODE_vALUE=0;
    protected final String ERROR_MSG="emsg";
    protected final String EMPTY_MSG="";

    protected final int NETWORK_ERROR=-1;
    protected final int JSON_ERROR=-2;
    protected final int OTHER_ERROR=-3;

    private Handler mDeliveryHandler;
    private DisposeDataListener listener;
    private Class<?>aClass;

    public CommonJsonCallback(DisposeDataHandle handle){
        this.listener=handle.listener;
        this.aClass=handle.aClass;
        this.mDeliveryHandler=new Handler(Looper.getMainLooper());

    }

    public CommonJsonCallback(){

    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final  String result=response.body().toString();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(Object responseobj){
        if(responseobj==null&&responseobj.toString().trim().equals("")){
            listener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
        }
        try {
            JSONObject result=new JSONObject(responseobj.toString());
            if(result.has(RESULT_CODE)){
                if(result.getInt(RESULT_CODE)==RESULT_CODE_vALUE){
                    if(aClass==null){
                        listener.onSucess(responseobj);

                        System.out.println("callback Success"+responseobj.toString());
                    }else{
//                        Object object=ResponseEntityToModule.parseIsonObjectModule(result,aClass);
                        Gson gson=new Gson();
                        Object object=gson.fromJson(result.toString(),aClass.getClass());//fromjson参数正确是待定！！！！！
                        if(object!=null){
                         listener.onSucess(object);

                            System.out.println("callback failure : json为空");
                        }else {
                            //返回的不是合法json
                            listener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                            System.out.println("callback failure : json非法");
                        }
                    }
                }else {
                    System.out.println("第一层 if");
                    listener.onFailure(new OkHttpException(OTHER_ERROR,result.get(RESULT_CODE)));
                }
            }
        } catch (JSONException e) {
            listener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage() ));
//            e.printStackTrace();

        }
    }

}
