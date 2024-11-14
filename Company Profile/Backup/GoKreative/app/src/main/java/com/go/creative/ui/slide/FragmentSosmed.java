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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FragmentSosmed extends BottomSheetDialogFragment {

    TextInputEditText inputSosmed, inputAkun, inputLink;
    TextView button;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sosmed, container, false);

        inputSosmed = view.findViewById(R.id.fragment_sosmed_nama);
        inputAkun = view.findViewById(R.id.fragment_sosmed_akun);
        inputLink = view.findViewById(R.id.fragment_sosmed_link);
        button = view.findViewById(R.id.fragment_sosmed_save);
        progress = view.findViewById(R.id.fragment_sosmed_progress);

        button.setOnClickListener(view1 -> {
            progress.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
            map.put("sosmed", inputSosmed.getText().toString());
            map.put("nama", inputAkun.getText().toString());
            map.put("link", inputLink.getText().toString());

            FirebaseFirestore.getInstance().collection("go_creative").document("sosmed").collection("-").add(map).addOnCompleteListener(task -> {
                progress.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Kamu berhasil nambah sosmed", Toast.LENGTH_LONG).show();
                    dismiss();
                } else
                    Toast.makeText(getContext(), "Kamu gagal nambah sosmed", Toast.LENGTH_LONG).show();
            });
        });

        return view;
    }

}