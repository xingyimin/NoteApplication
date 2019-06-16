package com.example.ruler.noteapplication.Bean;

import android.graphics.Bitmap;

public class UserSetting {
    private Bitmap bitmap;
    private String name;
    public UserSetting(String name,Bitmap bitmap){
        this.name=name;
        this.bitmap=bitmap;
    }

    public UserSetting(){

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
