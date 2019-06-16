package com.example.ruler.noteapplication.BmobBean;

import android.support.annotation.NonNull;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

public class Tag extends BmobObject implements Comparable<Tag>{
    private Integer id;
    private String name;

    private Integer noteId;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    @Override
    public int compareTo(@NonNull Tag o) {
        return 0;
    }
}
