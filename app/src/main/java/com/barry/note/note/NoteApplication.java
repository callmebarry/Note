package com.barry.note.note;

import android.app.Application;

import com.barry.note.database.DBManager;


/**
 * Created by Administrator on 2017/5/3.
 */

public class NoteApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().initDatabase(getApplicationContext());
          }

}
