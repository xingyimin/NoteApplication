package com.example.ruler.noteapplication.BmobBean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Note extends BmobObject{
    private Integer id;

    private String title;

    private String content;

    private String author;

    private BmobDate createdate;

    private Integer noteBookId;

    private Tag tag;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public Integer getNoteBookId() {
        return noteBookId;
    }

    public void setNoteBookId(Integer noteBookId) {
        this.noteBookId = noteBookId;
    }

    public BmobDate getCreatedate() {
        return createdate;
    }

    public void setCreatedate(BmobDate createdate) {
        this.createdate = createdate;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
