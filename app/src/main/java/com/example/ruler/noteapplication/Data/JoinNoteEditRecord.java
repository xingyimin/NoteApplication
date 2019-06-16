package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class JoinNoteEditRecord {
    @Id
    Long Id;
    Long EditId;
    Long noteId;
    @Generated(hash = 1035967184)
    public JoinNoteEditRecord(Long Id, Long EditId, Long noteId) {
        this.Id = Id;
        this.EditId = EditId;
        this.noteId = noteId;
    }
    @Generated(hash = 1007069461)
    public JoinNoteEditRecord() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public Long getEditId() {
        return this.EditId;
    }
    public void setEditId(Long EditId) {
        this.EditId = EditId;
    }
    public Long getNoteId() {
        return this.noteId;
    }
    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }
}
