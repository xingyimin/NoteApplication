package com.example.ruler.noteapplication.Data;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "JOIN_NOTE_EDIT_RECORD".
*/
public class JoinNoteEditRecordDao extends AbstractDao<JoinNoteEditRecord, Long> {

    public static final String TABLENAME = "JOIN_NOTE_EDIT_RECORD";

    /**
     * Properties of entity JoinNoteEditRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "Id", true, "_id");
        public final static Property EditId = new Property(1, Long.class, "EditId", false, "EDIT_ID");
        public final static Property NoteId = new Property(2, Long.class, "noteId", false, "NOTE_ID");
    }


    public JoinNoteEditRecordDao(DaoConfig config) {
        super(config);
    }
    
    public JoinNoteEditRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"JOIN_NOTE_EDIT_RECORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: Id
                "\"EDIT_ID\" INTEGER," + // 1: EditId
                "\"NOTE_ID\" INTEGER);"); // 2: noteId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"JOIN_NOTE_EDIT_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, JoinNoteEditRecord entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
 
        Long EditId = entity.getEditId();
        if (EditId != null) {
            stmt.bindLong(2, EditId);
        }
 
        Long noteId = entity.getNoteId();
        if (noteId != null) {
            stmt.bindLong(3, noteId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, JoinNoteEditRecord entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
 
        Long EditId = entity.getEditId();
        if (EditId != null) {
            stmt.bindLong(2, EditId);
        }
 
        Long noteId = entity.getNoteId();
        if (noteId != null) {
            stmt.bindLong(3, noteId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public JoinNoteEditRecord readEntity(Cursor cursor, int offset) {
        JoinNoteEditRecord entity = new JoinNoteEditRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // EditId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // noteId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, JoinNoteEditRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEditId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNoteId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(JoinNoteEditRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(JoinNoteEditRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(JoinNoteEditRecord entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}