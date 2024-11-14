package com.catering.dessert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String CATERING_DESSERT = "CATERING_DESSERT";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, CATERING_DESSERT, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_dessert = "CREATE TABLE 'tbl_dessert' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'photo' TEXT(100) NOT NULL," +
                "'name' TEXT(100) NOT NULL," +
                "'price' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_dessert);

        String tbl_dessert_order = "CREATE TABLE 'tbl_dessert_order' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'photo' TEXT(100) NOT NULL," +
                "'name' TEXT(100) NOT NULL," +
                "'price' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_dessert_order);

        String tbl_sandi = "CREATE TABLE 'tbl_sandi' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'password' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_sandi);
        sqLiteDatabase.execSQL("INSERT INTO tbl_sandi (id,password) VALUES(" + "'0'," + "'ftik')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
