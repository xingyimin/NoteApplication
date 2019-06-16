package com.example.ruler.noteapplication.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtils {
    public static String getVersionName(Context context){
        String versionName="1.0.0";
        try {
            PackageManager packageManager=context.getPackageManager();
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return versionName;
    }
}
