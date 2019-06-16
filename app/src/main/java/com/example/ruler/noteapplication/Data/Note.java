package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


@Entity
public class Note {
    @Id
    private Long id;

    private String title;

    private String content;

    private String author;

    private Date createdate;

    private long noteBookId;

    private long tagId;

    @ToMany
    @JoinEntity(
            entity = JoinNoteTag.class,
            sourceProperty = "noteId",
            targetProperty = "tagId"
    )
    private List<Tag>tags ;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 363862535)
    private transient NoteDao myDao;


    @Generated(hash = 42227474)
    public Note(Long id, String title, String content, String author,
            Date createdate, long noteBookId, long tagId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdate = createdate;
        this.noteBookId = noteBookId;
        this.tagId = tagId;
    }


    @Generated(hash = 1272611929)
    public Note() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return this.content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getAuthor() {
        return this.author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public Date getCreatedate() {
        return this.createdate;
    }


    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }


    public long getNoteBookId() {
        return this.noteBookId;
    }


    public void setNoteBookId(long noteBookId) {
        this.noteBookId = noteBookId;
    }


    public long getTagId() {
        return this.tagId;
    }


    public void setTagId(long tagId) {
        this.tagId = tagId;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1070775174)
    public List<Tag> getTags() {
        if (tags == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagDao targetDao = daoSession.getTagDao();
            List<Tag> tagsNew = targetDao._queryNote_Tags(id);
            synchronized (this) {
                if (tags == null) {
                    tags = tagsNew;
                }
            }
        }
        return tags;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 404234)
    public synchronized void resetTags() {
        tags = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 799086675)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteDao() : null;
    }
}
