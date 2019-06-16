package com.example.ruler.noteapplication.Utils;

import android.Manifest;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.ruler.noteapplication.MainActivity;

import cn.bmob.v3.Bmob;

public class BmobUtils {

    public void init(Context context){
        Bmob.initialize(context,"http://doc.bmob.cn/data/android/index.html");
    }
}
