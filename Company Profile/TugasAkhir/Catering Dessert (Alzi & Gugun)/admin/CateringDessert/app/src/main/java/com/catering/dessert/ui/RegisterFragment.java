package com.catering.dessert.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.catering.dessert.DBConfig;
import com.catering.dessert.R;
import com.catering.dessert.Storage;

public class RegisterFragment extends Fragment {

    View view;
    ProgressBar progressBar;
    EditText editPassword;
    TextView textMessage;
    String password;
    DBConfig config;
    Cursor cr;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        config = new DBConfig(getContext(), DBConfig.CATERING_DESSERT, null, DBConfig.DB_VERSION);
        editPassword = view.findViewById(R.id.fragment_register_edit);
        progressBar = view.findViewById(R.id.fragment_register_progressbar);
        textMessage = view.findViewById(R.id.fragment_register_message);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_sandi", null);
        cr.moveToFirst();
        cr.moveToPosition(cr.getCount()-1);
        password = cr.getString(1);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editPassword.removeTextChangedListener(this);
                if (editPassword.getText().toString().equals(password)) {
                    new Storage(getContext()).setLogin(true);
                }
                editPassword.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }

}