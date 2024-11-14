package com.go.creative.ui.slide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.go.creative.R;
import com.go.creative.other.DBConfig;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentSosmed extends BottomSheetDialogFragment {

    TextInputEditText inputSosmed, inputAkun, inputLink;
    TextView button;
    ProgressBar progress;
    DBConfig config;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sosmed, container, false);
        config = new DBConfig(getContext(), DBConfig.GO_CREATIVE, null, DBConfig.DB_VERSION);
        inputSosmed = view.findViewById(R.id.fragment_sosmed_nama);
        inputAkun = view.findViewById(R.id.fragment_sosmed_akun);
        inputLink = view.findViewById(R.id.fragment_sosmed_link);
        button = view.findViewById(R.id.fragment_sosmed_save);
        progress = view.findViewById(R.id.fragment_sosmed_progress);

        button.setOnClickListener(view1 -> {
            progress.setVisibility(View.VISIBLE);
            config.getWritableDatabase().execSQL("INSERT INTO tbl_sosmed (sosmed,nama,link) VALUES(" +
                    "'" + inputSosmed.getText().toString() + "'," +
                    "'" + inputAkun.getText().toString() + "'," +
                    "'" + inputLink.getText().toString() + "')");
            progress.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Kamu berhasil nambah sosmed", Toast.LENGTH_LONG).show();
            dismiss();
        });

        return view;
    }

}