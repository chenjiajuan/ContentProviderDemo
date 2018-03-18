package com.cjj.server;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenjiajuan on 2018/3/18.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "person_list.db";
    public final static String TABLE_NAME = "person";
    public final static String TABLE_STUDENT = "student";
    private final static int DB_VERSION = 2;
    private final String SQL_CREATE_TABLE_PERSION = "create table if not exists " + TABLE_NAME + "(_id integer primary key, name TEXT, description TEXT)";
    private final String SQL_CREATE_TABLE_STUDENT = "create table if not exists " + TABLE_STUDENT + "(_id integer primary key, name TEXT, description TEXT)";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PERSION);
        db.execSQL(SQL_CREATE_TABLE_STUDENT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
