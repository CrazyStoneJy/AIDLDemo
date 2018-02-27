package me.crazystone.study.contentproviderserver;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by crazy_stone on 18-2-27.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_USER = "user";
    public static final String TABLE_JOB = "job";
    public static final int DB_VERSION = 1;
    static final String DB_NAME = "test.db";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_user = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(id integer primary key autoincrement,name text)";
        String sql_job = "CREATE TABLE IF NOT EXISTS " + TABLE_JOB + "(id integer primary key autoincrement,job text)";
        db.execSQL(sql_user);
        db.execSQL(sql_job);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
