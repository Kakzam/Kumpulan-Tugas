package com.uti.psqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewMahasiswaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewMahasiswaFragment extends Fragment {
    //  deklarasi variabel komponen
    @BindView(R.id.rcv_data)
    RecyclerView rcv_data;

    //  deklarasi variabel class
    DBConfig config;
    //  deklarasi variabel sql
    SQLiteDatabase db;
    //  deklarasi variabel cursor
    //  cursor digunakan untuk cek posisi record
    Cursor cr;

    //    deklarasi variabel List dan Array Adapter
    List<MahasiswaModel> list;
    MahasiswaAdapter adp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewMahasiswaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewMahasiswaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewMahasiswaFragment newInstance(String param1, String param2) {
        ViewMahasiswaFragment fragment = new ViewMahasiswaFragment();
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

//    buat event "img_tambah"
    @OnClick(R.id.img_tambah)
    void tambah() {
//        set nama fragment yang aktif
        config.fr_aktif = "add_mhs";
//        tampilkan fragment "fragment_add_mahasiswa"
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_layout,new AddMahasiswaFragment()).commit();
    }

//    buat method "tampil"
    void tampil()
    {
//        baca isi data tabel "tb_mahasiswa"
        db = config.getReadableDatabase();
        cr = db.rawQuery("SELECT * FROM tb_mahasiswa",null);
//        mulai cursor dari awal record
        cr.moveToFirst();


//        tampilkan data mahasiswa dengan looping
        for(int count=0; count < cr.getCount(); count++) {
//        pindahkan cursor ke record berikutnya
            cr.moveToPosition(count);
            MahasiswaModel mdl = new MahasiswaModel();
//        kolom 0 = npm
//        kolom 1 = nama
//        kolom 3 = jurusan
            mdl.setNPM(cr.getString(0));
            mdl.setNama(cr.getString(1));
            mdl.setJurusan(cr.getString(2));

            list.add(mdl);
        }

//        rcv_data.setAdapter(adp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        definisi variabel View
        View vw = inflater.inflate(R.layout.fragment_view_mahasiswa, container, false);

        ButterKnife.bind(this,vw);

        //        definisi variabel config
        config = new DBConfig(getActivity(),DBConfig.db_name,null,DBConfig.db_version);
        //        definisi variabel List dan Array Adapter
        list = new ArrayList<>();
        adp = new MahasiswaAdapter(getContext(),list);
        //        setup recycler view
        RecyclerView.LayoutManager rcv_layout = new LinearLayoutManager(getContext());
        rcv_data.setLayoutManager(rcv_layout);
        rcv_data.setAdapter(adp);

        //        buat judul
        getActivity().setTitle("View Mahasiswa");

        //        hilangkan icon back (<-)
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

//        panggil method "tampil"
        tampil();

        return vw;
    }
}