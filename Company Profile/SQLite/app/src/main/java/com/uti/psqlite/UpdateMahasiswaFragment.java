package com.uti.psqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateMahasiswaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateMahasiswaFragment extends Fragment {
    //    deklarasi variabel komponen
    @BindView(R.id.edt_npm)
    TextInputEditText edt_npm;
    @BindView(R.id.edt_nama)
    TextInputEditText edt_nama;
    @BindView(R.id.spn_jurusan)
    Spinner spn_jurusan;

    //  deklarasi array untuk isi/pilihan Spinner
    String[] jurusan = {"Pilih Jurusan","IF","SI","TI","TK","TE","TS"};

    //  deklarasi variabel class
    DBConfig config;
    //  deklarasi variabel sql
    SQLiteDatabase db;
    //  deklarasi variabel cursor
    //  cursor digunakan untuk cek posisi record
    Cursor cr;
    ArrayAdapter adp;
    String npm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateMahasiswaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateMahasiswaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateMahasiswaFragment newInstance(String param1, String param2) {
        UpdateMahasiswaFragment fragment = new UpdateMahasiswaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    buat event "img_tampil"
    @OnClick(R.id.img_tampil)
    void tampil() {
//        set nama fragment yang aktif
        config.fr_aktif = "view_mhs";
        // panggil fragment "view_mahasiswa"
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout,new ViewMahasiswaFragment()).commit();
    }

    //    buat event "btn_batal"
    @OnClick(R.id.btn_batal)
    void batal() {
//        cek data mahasiswa
//        definisi variabel sql
        db = config.getReadableDatabase();
//        cari data npm dari database
        cr = db.rawQuery("SELECT * FROM tb_mahasiswa WHERE npm = '"+edt_npm.getText().toString()+"'",null);
        cr.moveToFirst();

//        jika npm ditemukan
        if(cr.getCount() != 0)
        {
//        kolom 0 = npm
//        kolom 1 = nama
//        kolom 3 = jurusan
            edt_npm.setText(cr.getString(0));
            edt_nama.setText(cr.getString(1));
            spn_jurusan.setSelection(adp.getPosition(cr.getString(2)));
//            variabel "npm" digunakan untuk menyimpan data "npm" yang lama
            npm = edt_npm.getText().toString();

        }
//            jika npm tidak ditemukan
        else
        {
//            panggil method "tampil"
            tampil();
        }
    }

    //    buat event "btn_ubah"
    @OnClick(R.id.btn_ubah)
    void ubah()
    {
//        cek apakah semua komponen sudah terisi atau belum
//        jika komponen ada yang belum terisi
        if(edt_npm.getText().toString().isEmpty() ||
                edt_nama.getText().toString().isEmpty() ||
                spn_jurusan.getSelectedItemId() == 0)
        {
            Toast.makeText(getContext(), "Seluruh Data Wajib Diisi/Dipilih !", Toast.LENGTH_SHORT).show();
        }
//        jika semua komponen sudah terisi
        else
        {
//        cek apakah npm yang diisi sudah pernah tersimpan di database
//        definisi variabel sql
            db = config.getReadableDatabase();
//        cari data npm dari database
            cr = db.rawQuery("SELECT npm FROM tb_mahasiswa WHERE npm = '"+edt_npm.getText().toString()+"' AND npm != '"+npm+"'",null);

            cr.moveToFirst();

//            jika npm ditemukan
            if(cr.getCount() != 0)
            {
                Toast.makeText(getContext(), "NPM Sudah Pernah Tersimpan !", Toast.LENGTH_SHORT).show();
            }
//            jika npm tidak ditemukan
            else
            {
//              proses simpan ke database
                db = config.getWritableDatabase();
                db.execSQL("UPDATE tb_mahasiswa SET " +
                        "npm = '"+edt_npm.getText().toString()+"'," +
                        "nama = '"+edt_nama.getText().toString()+"'," +
                        "jurusan = '"+spn_jurusan.getSelectedItem().toString()+"'" +
                        " WHERE npm = '"+npm+"'");
                Toast.makeText(getContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();

                batal();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // definisi variabel View
        View vw = inflater.inflate(R.layout.fragment_update_mahasiswa, container, false);

        ButterKnife.bind(this,vw);

//      memasukkan array jurusan ke dalam spinner
        adp = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                jurusan);

        spn_jurusan.setAdapter(adp);

//        definisi variabel class
        config = new DBConfig(getActivity(),DBConfig.db_name,null,DBConfig.db_version);

        // buat judul
        getActivity().setTitle("Ubah Mahasiswa");

        // buat icon back (<-)
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        untuk mengaktifkan fungsi icon back (<-)
        setHasOptionsMenu(true);

//        panggil method "awal"
        awal();

        return vw;
    }

    //    method untuk icon back (<-)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                panggil method "tampil"
                tampil();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    method untuk pembacaan data awal
    void awal() {
//        baca variabel Bundle dari MahasiswaAdapter
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            edt_npm.setText(bundle.getString("npm"));
            edt_nama.setText(bundle.getString("nama"));
            spn_jurusan.setSelection(adp.getPosition(bundle.getString("jurusan")));
//            variabel "npm" digunakan untuk menyimpan data "npm" yang lama
            npm = bundle.getString("npm");
        }
    }
}