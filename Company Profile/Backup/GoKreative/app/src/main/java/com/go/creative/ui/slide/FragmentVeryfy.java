package com.go.creative.ui.slide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.go.creative.R;
import com.go.creative.ui.MainPrimer;
import com.go.creative.ui.MainSekunder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentVeryfy extends BottomSheetDialogFragment {

    TextInputEditText inputPin;
    TextView button;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veryfy, container, false);

        inputPin = view.findViewById(R.id.fragment_veryfy_pin);
        button = view.findViewById(R.id.fragment_veryfy_button);
        progress = view.findViewById(R.id.fragment_veryfy_progress);

        button.setOnClickListener(view1 -> {
            progress.setVisibility(View.VISIBLE);
            FirebaseFirestore.getInstance().collection("go_creative").document("desain").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progress.setVisibility(View.GONE);

                    if (task.getResult().get("password").toString().equals(inputPin.getText().toString())) {
                        Toast.makeText(getContext(), "Kamu berhasil login", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), MainPrimer.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                        dismiss();
                    } else
                        Toast.makeText(getContext(), "Kamu gagal login", Toast.LENGTH_LONG).show();
                } else Toast.makeText(getContext(), "Kamu gagal login", Toast.LENGTH_LONG).show();
            });
        });

        return view;
    }

}