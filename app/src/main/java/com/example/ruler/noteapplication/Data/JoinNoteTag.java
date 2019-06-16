package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JoinNoteTag {
    @Id
    private Long id;

    private Long noteId;

    private Long tagId;

    @Generated(hash = 1521434379)
    public JoinNoteTag(Long id, Long noteId, Long tagId) {
        this.id = id;
        this.noteId = noteId;
        this.tagId = tagId;
    }

    @Generated(hash = 941143881)
    public JoinNoteTag() {
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

    public long getTagId() {
        return this.tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

}
