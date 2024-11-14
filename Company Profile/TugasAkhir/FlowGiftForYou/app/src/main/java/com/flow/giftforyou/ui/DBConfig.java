package com.flow.giftforyou.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String FLOW_GIFT_FOR_YOU = "FLOW_GIFT_FOR_YOU.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, FLOW_GIFT_FOR_YOU, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_pelanggan = "CREATE TABLE 'tbl_pelanggan' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'nama' TEXT(100) NOT NULL," +
                "'login' TEXT(100) NOT NULL );";

        sqLiteDatabase.execSQL(tbl_pelanggan);

        String tbl_buket = "CREATE TABLE 'tbl_buket' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'judul' TEXT(100) NOT NULL," +
                "'deskripsi' TEXT(100) NOT NULL," +
                "'wa' TEXT(100) NOT NULL," +
                "'jpeg' TEXT(100) NOT NULL," +
                "'buket' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_buket);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
