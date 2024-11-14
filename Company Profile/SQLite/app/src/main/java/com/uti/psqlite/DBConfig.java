package com.uti.psqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {
//    buat konstanta
    public static final String db_name = "mahasiswa.db";
    public static final int db_version = 1;
//    buat variabel untuk baca fragment yang aktif
    public static String fr_aktif;

// buat konstruktor
    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        buat variabel
        String sql;
//        isi perintah/query
        sql = "CREATE TABLE 'tb_mahasiswa' (" +
                "'npm' TEXT(8) NOT NULL," +
                "'nama' TEXT(100) NOT NULL," +
                "'jurusan' TEXT(2) NOT NULL," +
                "PRIMARY KEY('npm')" +
                ");";
//        eksekusi query
        db.execSQL(sql);

//        simpan data
        sql = "INSERT INTO tb_mahasiswa VALUES('00000001','Budi Oke','IF')";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
