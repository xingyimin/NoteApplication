package com.example.ruler.noteapplication.Utils;

/*

管理andfix

 */

import android.content.Context;
import android.widget.EditText;

import com.alipay.euler.andfix.patch.PatchManager;

public class AndFixPatchManager {
    private static PatchManager patchManager=null;
    private static  AndFixPatchManager instance=null;

    public static  AndFixPatchManager getInstance(){
        if (instance==null){
            synchronized (AndFixPatchManager.class){
                if (instance==null){
                    instance=new AndFixPatchManager();
                }
            }
        }
        return  instance;
    }

    public void initPatch(Context context){
        patchManager =new PatchManager(context);
        patchManager.init(AppUtils.getVersionName(context));
        patchManager.loadPatch();
    }

    //对patch文件进行加载
    public void addPatch(String path){

        try {
            if(patchManager!=null){
                patchManager.addPatch(path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
