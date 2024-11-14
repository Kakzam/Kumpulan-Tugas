package com.sekud.id.base;

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

    public DBConfig get(Context context) {
        return new DBConfig(context, SEKUD_ID, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbl_user = "CREATE TABLE 'tbl_user' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'all_name' TEXT(100) NOT NULL," +
                "'phone_number' TEXT(100) NOT NULL," +
                "'permission' TEXT(100) NOT NULL );";

        sqLiteDatabase.execSQL(tbl_user);

        String tbl_car = "CREATE TABLE 'tbl_car' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'status' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'deskripsi' TEXT(100) NOT NULL," +
                "'dilihat' TEXT NOT NULL," +
                "'photo' TEXT NOT NULL," +
                "'menu' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_car);

        String tbl_transaction = "CREATE TABLE 'tbl_transaction' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'status' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'deskripsi' TEXT(100) NOT NULL," +
                "'dilihat' TEXT NOT NULL," +
                "'photo' TEXT NOT NULL," +
                "'id_pelanggan' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_transaction);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
