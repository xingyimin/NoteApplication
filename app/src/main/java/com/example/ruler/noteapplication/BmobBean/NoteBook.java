package com.example.ruler.noteapplication.BmobBean;

import com.example.ruler.noteapplication.Data.Note;

import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class NoteBook extends BmobObject{
    private Integer id;

    private String title;

    private String name;

    private Integer notebookId;

    private Integer noteId;

    private List<Note> notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(Integer notebookId) {
        this.notebookId = notebookId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
