package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Mark {
    @Id
    Long id;
    long noteId;
    @Generated(hash = 1429976637)
    public Mark(Long id, long noteId) {
        this.id = id;
        this.noteId = noteId;
    }
    @Generated(hash = 1181118646)
    public Mark() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getNoteId() {
        return this.noteId;
    }
    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
