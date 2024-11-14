package com.mobile.skud_id.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String SEKUD_ID = "SEKUD_ID";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, SEKUD_ID, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /* PERMISSION
         *  1. ADMIN
         *  2. CUSTOMER
         * */
        String tbl_user = "CREATE TABLE 'tbl_user' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'phone_number' TEXT(100) NOT NULL," +
                "'image' TEXT(100) NOT NULL," +
                "'permission' TEXT(100) NOT NULL );";

        sqLiteDatabase.execSQL(tbl_user);

        String tbl_item = "CREATE TABLE 'tbl_item' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'title' TEXT(100) NOT NULL," +
                "'usage' TEXT(100) NOT NULL," +
                "'price' TEXT(100) NOT NULL," +
                "'advantage' TEXT(100) NOT NULL," +
                "'deficiency' TEXT NOT NULL," +
                "'image' TEXT NOT NULL," +
                "'check_new' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_item);

        String tbl_image = "CREATE TABLE 'tbl_image' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'image' TEXT(100) NOT NULL," +
                "'id_item' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_image);

        String tbl_location = "CREATE TABLE 'tbl_location' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'link' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_location);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
