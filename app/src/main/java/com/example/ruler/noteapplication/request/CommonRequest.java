package com.example.ruler.noteapplication.request;


import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/*
*
* 接收参数，生成request对象
* */
public class CommonRequest {
    public static Request createPostRequest(String url, RequestParams params){
        FormBody.Builder mFormBodyBuild=new FormBody.Builder();
        if(params!=null){
            for(Map.Entry<String,String>entry:params.urlParams.entrySet()){
                mFormBodyBuild.add(entry.getKey(),entry.getValue());
            }
        }
        FormBody formBody=mFormBodyBuild.build();
        return  new Request.Builder().url(url).post(formBody).build();

    }

    public static Request createGetRequest(String url, RequestParams params){
        StringBuilder urlBuilder=new StringBuilder(url).append("?");
        if(params!=null){
            for(Map.Entry<String,String>entry:params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        return  new Request.Builder().url(urlBuilder.substring(0,urlBuilder.length()-1)).get().build();
    }

    public static Request createMonitorRequset(String url, RequestParams params){
        StringBuilder urlBuilder = new StringBuilder(url).append("&");
        if (params != null && params.hasParams()) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }
}
