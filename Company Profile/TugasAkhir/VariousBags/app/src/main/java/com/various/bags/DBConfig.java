package com.various.bags;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String VARIOUS_BAGS = "VARIOUS_BAGS.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, VARIOUS_BAGS, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbl_user = "CREATE TABLE 'tbl_user' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'name' TEXT(100) NOT NULL," +
                "'phone' TEXT(100) NOT NULL );";

        sqLiteDatabase.execSQL(tbl_user);

        String tbl_trolly = "CREATE TABLE 'tbl_trolly' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'id_user' TEXT(100) NOT NULL," +
                "'nama_item' TEXT(100) NOT NULL," +
                "'price' TEXT(100) NOT NULL," +
                "'status' TEXT(100) NOT NULL," +
                "'see' TEXT NOT NULL," +
                "'image' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_trolly);

        String tbl_bags = "CREATE TABLE 'tbl_bags' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama_item' TEXT(100) NOT NULL," +
                "'price' TEXT(100) NOT NULL," +
                "'status' TEXT(100) NOT NULL," +
                "'see' TEXT NOT NULL," +
                "'image' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_bags);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
