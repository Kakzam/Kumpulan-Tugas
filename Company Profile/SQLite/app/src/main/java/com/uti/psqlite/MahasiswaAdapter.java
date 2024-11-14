package com.uti.psqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>{

//    deklarasi variabel
    Context context;
    List<MahasiswaModel> list;
//  deklarasi variabel class
    DBConfig config;
//  deklarasi variabel sql
    SQLiteDatabase db;

//    buat konstruktor
    public MahasiswaAdapter(Context ctx,List<MahasiswaModel> lst)
    {
        this.context = ctx;
        this.list = lst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        hubungkan layout "list_mahasiswa" ke adapter
        View vw = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_mahasiswa, viewGroup, false);
        ViewHolder holder = new ViewHolder(vw);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MahasiswaModel mdl = list.get(i);
//        isi komponen layout "list_mahasiswa"
        viewHolder.txt_npm.setText(mdl.getNPM());
        viewHolder.txt_nama.setText(mdl.getNama());
        viewHolder.txt_jurusan.setText(mdl.getJurusan());

//        event saat data dipilih
        viewHolder.crv_data.setOnClickListener(v -> {
//            set nama fragment yang aktif
            config.fr_aktif = "update_mhs";

//            kirim data ke "fragment_update_mahasiswa"
            Bundle bdl = new Bundle();
//            keterangan
//            bdl.putString("xyz", abc);
//            "xyz" -> nama parameter yang akan dibaca pada "fragment_update_mahasiswa"
//            abc -> isi dari parameter yang akan dibaca pada "fragment_update_mahasiswa"
            bdl.putString("npm", mdl.getNPM());
            bdl.putString("nama", mdl.getNama());
            bdl.putString("jurusan", mdl.getJurusan());

//            deklarasi variabel fragment
            Fragment fragment;
            fragment = new UpdateMahasiswaFragment();
            fragment.setArguments(bdl);

//            alihkan ke "fragment_update_mahasiswa"
            AppCompatActivity act = (AppCompatActivity) v.getContext();
            act.getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout,fragment).commit();
        });

//        event saat tombol hapus di klik
        viewHolder.img_hapus.setOnClickListener(v -> {
//            tampilkan pesan
            new AlertDialog.Builder(context)
//            set message
                    .setMessage("Data Mahasiswa : "+viewHolder.txt_npm.getText().toString()+"\nIngin Dihapus ?")
//set positive button
                    .setPositiveButton("Ya", (dialogInterface, i1) -> {
                        config = new DBConfig(context,DBConfig.db_name,null,DBConfig.db_version);
                        //  hapus data
                        db = config.getWritableDatabase();
                        db.execSQL("DELETE FROM tb_mahasiswa WHERE npm = '"+viewHolder.txt_npm.getText().toString()+"'");

                        //  refresh recycler view
                        list.remove(i);
                        notifyItemRemoved(i);

                        Toast.makeText(context, "Data Mahasiswa Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    })
//set negative button
                    .setNegativeButton("Tidak", (dialogInterface, i12) -> {
                        dialogInterface.cancel();
                    })
                    .show();
        });

    }

    @Override
    public int getItemCount() {
//        kirim total jumlah record pada list
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
//        deklarasi komponen layout "list_mahasiswa"
        @BindView(R.id.txt_npm)
        TextView txt_npm;
        @BindView(R.id.txt_nama)
        TextView txt_nama;
        @BindView(R.id.txt_jurusan)
        TextView txt_jurusan;
        @BindView(R.id.img_hapus)
        ImageView img_hapus;
        @BindView(R.id.crv_data)
        CardView crv_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
