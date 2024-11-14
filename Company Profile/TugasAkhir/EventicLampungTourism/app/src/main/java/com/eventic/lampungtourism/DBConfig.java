package com.eventic.lampungtourism;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String EVENTIC_LAMPUNG_TOURISM = "EVENTIC_LAMPUNG_TOURISM.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, EVENTIC_LAMPUNG_TOURISM, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbl_user = "CREATE TABLE 'tbl_user' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'id_id' TEXT(100) NOT NULL," +
                "'nama' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'position' TEXT(100) NOT NULL );";

        sqLiteDatabase.execSQL(tbl_user);

        String tbl_event = "CREATE TABLE 'tbl_event' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'judul' TEXT(100) NOT NULL," +
                "'description' TEXT(100) NOT NULL," +
                "'price' TEXT(100) NOT NULL," +
                "'type' TEXT(100) NOT NULL," +
                "'image' TEXT NOT NULL," +
                "'image2' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_event);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
