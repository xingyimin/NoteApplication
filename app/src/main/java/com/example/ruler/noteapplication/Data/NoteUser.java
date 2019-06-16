package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Id;

public class NoteUser {
    @Id
    private Long id;
    private String userObjectId;
    private String userName;
    private String passWord;
}
