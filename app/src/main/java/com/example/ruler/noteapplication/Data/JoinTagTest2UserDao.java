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
 * DAO for table "JOIN_TAG_TEST2_USER".
*/
public class JoinTagTest2UserDao extends AbstractDao<JoinTagTest2User, Long> {

    public static final String TABLENAME = "JOIN_TAG_TEST2_USER";

    /**
     * Properties of entity JoinTagTest2User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, Long.class, "userId", false, "USER_ID");
        public final static Property TagId = new Property(2, Long.class, "tagId", false, "TAG_ID");
    }


    public JoinTagTest2UserDao(DaoConfig config) {
        super(config);
    }
    
    public JoinTagTest2UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"JOIN_TAG_TEST2_USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" INTEGER," + // 1: userId
                "\"TAG_ID\" INTEGER);"); // 2: tagId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"JOIN_TAG_TEST2_USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, JoinTagTest2User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
 
        Long tagId = entity.getTagId();
        if (tagId != null) {
            stmt.bindLong(3, tagId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, JoinTagTest2User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
 
        Long tagId = entity.getTagId();
        if (tagId != null) {
            stmt.bindLong(3, tagId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public JoinTagTest2User readEntity(Cursor cursor, int offset) {
        JoinTagTest2User entity = new JoinTagTest2User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // tagId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, JoinTagTest2User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTagId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(JoinTagTest2User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(JoinTagTest2User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(JoinTagTest2User entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}