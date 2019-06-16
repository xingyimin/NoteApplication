package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

import cn.bmob.v3.BmobObject;

@Entity
public class EditRecord {
    @Id
    private Long EditId;
    private String boforeUpdateContent;
    private String afterUpdateContent;
    private Date lastUpdateAt;
    private long noteId;
    @Generated(hash = 1610690077)
    public EditRecord(Long EditId, String boforeUpdateContent,
            String afterUpdateContent, Date lastUpdateAt, long noteId) {
        this.EditId = EditId;
        this.boforeUpdateContent = boforeUpdateContent;
        this.afterUpdateContent = afterUpdateContent;
        this.lastUpdateAt = lastUpdateAt;
        this.noteId = noteId;
    }
    @Generated(hash = 528210399)
    public EditRecord() {
    }
    public Long getEditId() {
        return this.EditId;
    }
    public void setEditId(Long EditId) {
        this.EditId = EditId;
    }
    public String getBoforeUpdateContent() {
        return this.boforeUpdateContent;
    }
    public void setBoforeUpdateContent(String boforeUpdateContent) {
        this.boforeUpdateContent = boforeUpdateContent;
    }
    public String getAfterUpdateContent() {
        return this.afterUpdateContent;
    }
    public void setAfterUpdateContent(String afterUpdateContent) {
        this.afterUpdateContent = afterUpdateContent;
    }
    public Date getLastUpdateAt() {
        return this.lastUpdateAt;
    }
    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
    public long getNoteId() {
        return this.noteId;
    }
    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
