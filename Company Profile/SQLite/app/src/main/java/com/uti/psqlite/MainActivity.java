package com.uti.psqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
//  deklarasi variabel class
    DBConfig config;
//    deklarasi variabel sql
//    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        definisi variabel class
        config = new DBConfig(getApplication(),DBConfig.db_name,null,DBConfig.db_version);
//        definisi variabel sql
//        db = config.getReadableDatabase();

//        set nama fragment yang aktif
        config.fr_aktif = "view_mhs";
//        panggil fragment "view_mahasiswa"
        getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout,new ViewMahasiswaFragment()).commit();
    }

//    event tombol back pada device
    @Override
    public void onBackPressed(){
//        jika fragment yang aktif = "fragment_add_mahasiswa"
        if(config.fr_aktif.equals("add_mhs") || config.fr_aktif.equals("update_mhs"))
        {
            //        set nama fragment yang aktif
            config.fr_aktif = "view_mhs";
            getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout,new ViewMahasiswaFragment()).commit();
        }
        else
        {
            super.onBackPressed();
        }

    }
}