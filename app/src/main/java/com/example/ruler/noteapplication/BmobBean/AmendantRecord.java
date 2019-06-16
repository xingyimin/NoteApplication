package com.example.ruler.noteapplication.BmobBean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class AmendantRecord extends BmobObject{
    private String boforeUpdateContent;
    private String afterUpdateContent;
    private BmobDate lastUpdateAt;
    private Integer noteId;
    private String userId;
    private String userName;

    public String getBoforeUpdateContent() {
        return boforeUpdateContent;
    }

    public void setBoforeUpdateContent(String boforeUpdateContent) {
        this.boforeUpdateContent = boforeUpdateContent;
    }

    public String getAfterUpdateContent() {
        return afterUpdateContent;
    }

    public void setAfterUpdateContent(String afterUpdateContent) {
        this.afterUpdateContent = afterUpdateContent;
    }


    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BmobDate getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(BmobDate lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
}
