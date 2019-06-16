package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class JoinTagTest2User {
    @Id
    private Long id;

    private Long userId;

    private Long tagId;

    @Generated(hash = 303223946)
    public JoinTagTest2User(Long id, Long userId, Long tagId) {
        this.id = id;
        this.userId = userId;
        this.tagId = tagId;
    }

    @Generated(hash = 345486335)
    public JoinTagTest2User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return this.tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

}
