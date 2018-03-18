package com.cjj.server;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import org.jetbrains.annotations.Nullable;

/**
 * Created by chenjiajuan on 2018/3/18.
 */

public class DataProvider extends ContentProvider {
    public final static String TABLE_NAME_PERSON = "person";
    public final static String TABLE_NAME_STUDENT= "student";
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.cjj.server.contentprovider";
    private static final Uri NOTIFY_URI = Uri.parse("content://" + AUTHORITY + "/student");
    private static final int TABLE_CODE_PERSON = 101;
    private static final int TABLE_CODE_STUDENT = 102;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    static {
        mUriMatcher.addURI(AUTHORITY, "person", TABLE_CODE_PERSON);
        mUriMatcher.addURI(AUTHORITY, "student", TABLE_CODE_STUDENT);
    }

    @Override
    public boolean onCreate() {
        context =getContext();
        sqLiteDatabase=new DBOpenHelper(context).getWritableDatabase();
        return false;

    }

    @Nullable
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs, String sortOrder) {
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_PERSON,null,null,null,null,null,null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, @Nullable ContentValues values) {
        int match=mUriMatcher.match(uri);
        switch (match){
            case TABLE_CODE_PERSON:
                long rawId= sqLiteDatabase.insert(TABLE_NAME_PERSON,null,values);
                Uri inserUri=ContentUris.withAppendedId(uri,rawId);
                if (rawId>0){
                    notifyDataChanged();
                    return inserUri;
                }
                break;
            case TABLE_CODE_STUDENT:
                long rwaId_2=sqLiteDatabase.insert(TABLE_NAME_STUDENT,null,values);
                Uri inserUri_2=ContentUris.withAppendedId(uri,rwaId_2);
                if (rwaId_2>0){
                    notifyDataChanged();
                    return inserUri_2;
                }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (mUriMatcher.match(uri)){
            case TABLE_CODE_PERSON:
                int count=sqLiteDatabase.delete(TABLE_NAME_PERSON,null,null);
                if (count>0){
                    notifyDataChanged();
                    return count;
                }
                break;
            case  TABLE_CODE_STUDENT:
                int count_2=sqLiteDatabase.delete(TABLE_NAME_STUDENT,null,null);
                if (count_2>0){
                    notifyDataChanged();
                    return count_2;
                }
                break;
            default:
                throw  new IllegalArgumentException("unkown uri "+uri.toString());
        }
        return 0;
    }

    private void notifyDataChanged() {
        getContext().getContentResolver().notifyChange(NOTIFY_URI,null);
    }

    @Override
    public int update(Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (mUriMatcher.match(uri)){
            case TABLE_CODE_PERSON:
                long name=ContentUris.parseId(uri);
                selection="name = ?";
                selectionArgs=new String[]{String.valueOf(name)};
                int count=sqLiteDatabase.update(TABLE_NAME_PERSON,values,selection,selectionArgs);
                if (count>0){
                    notifyDataChanged();
                }
                break;
            case TABLE_CODE_STUDENT:
                long name_2=ContentUris.parseId(uri);
                selection="description = ?";
                selectionArgs=new String[]{String.valueOf(name_2)};
                int count_2=sqLiteDatabase.update(TABLE_NAME_STUDENT,values,selection,selectionArgs);
                if (count_2>0){
                    notifyDataChanged();
                }

                break;
            default:
                throw  new IllegalArgumentException("UnKnow uri "+uri.toString());
        }
        return 0;
    }
}
