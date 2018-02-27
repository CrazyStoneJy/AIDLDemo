package me.crazystone.study.contentproviderserver;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by crazy_stone on 18-2-27.
 */

public class MyContentProvider extends ContentProvider {

    private static final int TYPE_USER = 0X11;
    private static final int TYPE_JOB = TYPE_USER + 1;
    private static final String AUTHORITY = "me.crazystone.study.contentproviderserver";
    UriMatcher matcher = null;
    DBHelper helper;
    SQLiteDatabase db;

    {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "user", TYPE_USER);
        matcher.addURI(AUTHORITY, "job", TYPE_JOB);
    }


    public String getTableFromUri(Uri uri) {
        int code = matcher.match(uri);
        String tableName = null;
        switch (code) {
            case TYPE_USER:
                return DBHelper.TABLE_USER;
            case TYPE_JOB:
                return DBHelper.TABLE_JOB;
            default:
                return tableName;
        }
    }

    @Override
    public boolean onCreate() {
        helper = new DBHelper(getContext(), DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        db = helper.getWritableDatabase();
        db.execSQL("delete from " + DBHelper.TABLE_JOB);
        db.execSQL("delete from " + DBHelper.TABLE_USER);
        db.execSQL("insert into " + DBHelper.TABLE_USER + "(name) values(\"jiayan\");");
        db.execSQL("insert into " + DBHelper.TABLE_JOB + "(job) values(\"androidn\");");
        db.execSQL("insert into " + DBHelper.TABLE_JOB + "(job) values(\"ios\");");

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableFromUri(uri);
        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableFromUri(uri);
        db.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
