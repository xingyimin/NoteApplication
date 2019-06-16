package com.example.ruler.noteapplication.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class IMUser extends BmobUser{
   private BmobFile imageHeaders;
   private String nickName;

    public IMUser(BmobFile imageHeaders) {
        this.imageHeaders = imageHeaders;
    }

    public IMUser() {

    }

    public BmobFile getImageHeaders() {
        return imageHeaders;
    }

    public void setImageHeaders(BmobFile imageHeaders) {
        this.imageHeaders = imageHeaders;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
