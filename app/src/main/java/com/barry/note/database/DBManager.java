package com.barry.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class DBManager {
    private static final java.lang.String DBNAME = "NOTEDB";
    public static DBManager sDatabaseManager;
    private DaoSession mDaoSession;


    private DBManager() {
    }

    public static DBManager getInstance() {
        if (sDatabaseManager == null) {
            synchronized (DBManager.class) {
                if (sDatabaseManager == null) {
                    sDatabaseManager = new DBManager();
                }
            }

        }
        return sDatabaseManager;
    }
    public void initDatabase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,DBNAME, null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();
    }
    public void saveNote(Note note) {
        NoteDao noteDao = mDaoSession.getNoteDao();
        noteDao.insert(note);
    }
    public void DeleteNote(Note note) {
        NoteDao noteDao = mDaoSession.getNoteDao();
        noteDao.delete(note);
    }

    public List<Note> queryNote(){
        NoteDao noteDao = mDaoSession.getNoteDao();
        return  noteDao.queryBuilder().list();
    }


    public void updataNote(Note note) {
        NoteDao noteDao = mDaoSession.getNoteDao();
        noteDao.update(note);
    }

    public List<Note> queryNoteByName(String str){
        NoteDao noteDao = mDaoSession.getNoteDao();
        QueryBuilder<Note> noteQueryBuilder = noteDao.queryBuilder();
        noteQueryBuilder.where(NoteDao.Properties.Title.like("%" + str + "%"));
        List<Note> list = noteQueryBuilder.list();
        return list;

    }

}
