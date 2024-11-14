package com.go.creative.ui.slide;

import android.content.Intent;
import android.database.Cursor;
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
import com.go.creative.ui.MainPrimer;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentVeryfy extends BottomSheetDialogFragment {

    TextInputEditText inputPin;
    TextView button;
    ProgressBar progress;
    DBConfig config;
    Cursor cr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veryfy, container, false);
        config = new DBConfig(getContext(), DBConfig.GO_CREATIVE, null, DBConfig.DB_VERSION);
        inputPin = view.findViewById(R.id.fragment_veryfy_pin);
        button = view.findViewById(R.id.fragment_veryfy_button);
        progress = view.findViewById(R.id.fragment_veryfy_progress);

        button.setOnClickListener(view1 -> {
            progress.setVisibility(View.VISIBLE);
            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pin", null);
            cr.moveToFirst();
            cr.moveToPosition(cr.getCount() - 1);
            if (cr.getString(1).equals(inputPin.getText().toString())) {
                Toast.makeText(getContext(), "Kamu berhasil login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainPrimer.class);
                getContext().startActivity(intent);
                getActivity().finish();
                progress.setVisibility(View.GONE);
                dismiss();
            } else Toast.makeText(getContext(), "Kamu gagal login", Toast.LENGTH_LONG).show();
        });

        return view;
    }

}