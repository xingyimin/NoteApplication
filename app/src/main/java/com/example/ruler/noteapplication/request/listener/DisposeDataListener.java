package com.example.ruler.noteapplication.request.listener;

public interface DisposeDataListener {
    public void onSucess(Object responseObj);

    public void onFailure(Object responseObj);

    void onProgress(int obj);
}
