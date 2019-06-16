package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;



@Entity
public class NoteBook  {

    @Id
    private Long id;

    private String title;

    private String name;

    private long notebookId;

    private long noteId;

    @ToMany(referencedJoinProperty = "noteBookId")
    private List<Note> notes;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1692630944)
    private transient NoteBookDao myDao;

    @Generated(hash = 1989971214)
    public NoteBook(Long id, String title, String name, long notebookId,
            long noteId) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.notebookId = notebookId;
        this.noteId = noteId;
    }

    @Generated(hash = 2066935268)
    public NoteBook() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNotebookId() {
        return this.notebookId;
    }

    public void setNotebookId(long notebookId) {
        this.notebookId = notebookId;
    }

    public long getNoteId() {
        return this.noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1887269379)
    public List<Note> getNotes() {
        if (notes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoteDao targetDao = daoSession.getNoteDao();
            List<Note> notesNew = targetDao._queryNoteBook_Notes(id);
            synchronized (this) {
                if (notes == null) {
                    notes = notesNew;
                }
            }
        }
        return notes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2032098259)
    public synchronized void resetNotes() {
        notes = null;
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
    @Generated(hash = 1888691330)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteBookDao() : null;
    }

}




