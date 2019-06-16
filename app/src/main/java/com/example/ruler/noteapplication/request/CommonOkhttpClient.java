package com.example.ruler.noteapplication.request;

import com.example.ruler.noteapplication.request.listener.CommonFileCallback;
import com.example.ruler.noteapplication.request.listener.CommonJsonCallback;
import com.example.ruler.noteapplication.request.listener.DisposeDataHandle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
*
* 请求的发送，请求参数的配置，https支持
* */
public class CommonOkhttpClient {
    private static final int TIME_OUT=30;
    private static OkHttpClient client;

    static {
        //填充超时事件
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);

        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request()
                        .newBuilder()
                        .addHeader("User-Agent","Imooc-Mobile")
                        .build();
                return chain.proceed(request);
            }
        });

        okHttpBuilder.followRedirects(true);
        okHttpBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        client=okHttpBuilder.build();

    }




    public static Call sendRequest(Request request, CommonJsonCallback commCallback){
        Call call=client.newCall(request);
        call.enqueue(commCallback);
        return call;
    }

    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call downLoadFile(Request request,DisposeDataHandle disposeDataHandle){
        Call call=client.newCall(request);
        call.enqueue((Callback) new CommonFileCallback(disposeDataHandle));
        return call;
    }

    public static Call post(Request request, DisposeDataHandle handle) {
        Call call =client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
}
