package com.sekud.id.feature.alert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sekud.id.R;
import com.sekud.id.base.BaseBottomSheetDialogFragment;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.feature.MainActivity;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;

public class DialogVerifikasiFragment extends BaseBottomSheetDialogFragment {

    TextInputEditText textName, textUsername, textPassword;
    TextView textBack, textLogin, textTitle;
    CheckBox checkBox;
    int verifikasi = 0;

    private static final String UPDATE = "UPDATE";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String NAME = "NAME";
    private static final String STATUS = "STATUS";
    private static final String ID = "ID";

    private String update;
    private String userName;
    private String password;
    private String name;
    private String status;
    private String id;

    public DialogVerifikasiFragment() {
        // Required empty public constructor
    }

    public static DialogVerifikasiFragment newInstance(String update, String userName, String password, String name, String status, String id) {
        DialogVerifikasiFragment fragment = new DialogVerifikasiFragment();
        Bundle args = new Bundle();
        args.putString(UPDATE, update);
        args.putString(USERNAME, userName);
        args.putString(PASSWORD, password);
        args.putString(NAME, name);
        args.putString(STATUS, status);
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            update = getArguments().getString(UPDATE);
            userName = getArguments().getString(USERNAME);
            password = getArguments().getString(PASSWORD);
            name = getArguments().getString(NAME);
            status = getArguments().getString(STATUS);
            id = getArguments().getString(ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        view = inflater.inflate(R.layout.dialog_verifikasi, container, false);
        loadingFragment = new LoadingFragment();

        setInisialisasi();
        return view;
    }

    private void setInisialisasi() {
        textName = view.findViewById(R.id.dialog_verifikasi_name);
        textUsername = view.findViewById(R.id.dialog_verifikasi_username);
        textPassword = view.findViewById(R.id.dialog_verifikasi_password);
        checkBox = view.findViewById(R.id.dialog_verifikasi_check);
        textBack = view.findViewById(R.id.dialog_verifikasi_back);
        textLogin = view.findViewById(R.id.dialog_verifikasi_verifikasi);
        textTitle = view.findViewById(R.id.dialog_verifikasi_title);

        textName.setText("");
        textUsername.setText("");
        textPassword.setText("");
        checkBox.setChecked(false);

        if (!new Preference(context).getLogin()) {
            textName.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
        } else {
            textTitle.setText("TAMBAH USER");
            textLogin.setText("SIMPAN");

            if (new Preference(context).getUpdateUser()) {
                textUsername.setText(new Preference(context).getUsername());
                new Preference(context).setUpdateUser(false);
            }

            if (new Preference(context).getUpdateUsername().equals("EMPTY")) {
                textName.setText(new Preference(context).getUpdateName());
                textUsername.setText(new Preference(context).getUpdateUsername());
                checkBox.setChecked(new Preference(context).getUpdateStatus());
            }
        }

        textBack.setOnClickListener(view -> {
            getDialog().dismiss();
        });

        textLogin.setOnClickListener(view -> {
            if (new Preference(context).getLogin()) {
                if (textName.getText().toString().isEmpty() ||
                        textUsername.getText().toString().isEmpty() ||
                        textPassword.getText().toString().isEmpty()) {
                    setAlerttDialog(StringUtil.WARNING, "Silahkan isi semua dengan benar.");
                } else {
                    if (new Preference(context).getUpdateUser()) {
                        String password = textPassword.getText().toString().isEmpty() ? new Preference(context).getPassword() : textPassword.getText().toString();
                        updateUser(
                                textUsername.getText().toString(),
                                password,
                                textName.getText().toString(),
                                checkBox.isChecked() + "",
                                new Preference(context).getId()
                        );
                    } else if (!new Preference(context).getUpdateUser() && !new Preference(context).getUpdateUsername().equals("EMPTY")) {
                        String password = textPassword.getText().toString().isEmpty() ? new Preference(context).getUpdatePassword() : textPassword.getText().toString();
                        updateUser(
                                textUsername.getText().toString(),
                                password,
                                textName.getText().toString(),
                                checkBox.isChecked() + "",
                                new Preference(context).getUpdateId());
                    } else new RestPresenter().addUser(textName.getText().toString(),
                            textUsername.getText().toString(),
                            textPassword.getText().toString(),
                            checkBox.isChecked() + "",
                            new RestCallback() {
                                @Override
                                public void Success(QuerySnapshot task) {
                                    setAlerttDialog(StringUtil.WARNING, "Berhasil Upload User " + textName.getText().toString());
                                    getDialog().dismiss();
                                }

                                @Override
                                public void Failed(String failed) {
                                    setAlerttDialog(StringUtil.WARNING, failed);
                                }

                                @Override
                                public void Failure(String failure) {
                                    setAlerttDialog(StringUtil.WARNING, failure);
                                }
                            });
                }
            } else {
                if (textUsername.getText().toString().isEmpty() ||
                        textPassword.getText().toString().isEmpty()) {
                    setAlerttDialog(StringUtil.WARNING, "Silahkan isi semua dengan benar.");
                } else {
                    new RestPresenter().getUser(new RestCallback() {
                        @Override
                        public void Success(QuerySnapshot task) {
                            verifikasi = task.getDocuments().size();
                            for (DocumentSnapshot documen : task.getDocuments()) {
                                verifikasi -= 1;
                                if (textUsername.getText().toString().equals(documen.get("username").toString()) &&
                                        textUsername.getText().toString().equals(documen.get("password").toString())) {
                                    new Preference(context).setLogin(true);
                                    new Preference(context).setName(documen.get("name").toString());
                                    new Preference(context).setUsername(documen.get("username").toString());
                                    new Preference(context).setPassword(documen.get("password").toString());
                                    new Preference(context).setStatus(Boolean.parseBoolean(documen.get("status").toString()));
                                    setActivity(MainActivity.class);
                                } else {
                                    if (verifikasi == 0)
                                        setAlerttDialog(StringUtil.WARNING, "Username dan Password anda salah");
                                }
                            }
                        }

                        @Override
                        public void Failed(String failed) {
                            setAlerttDialog(StringUtil.WARNING, failed);
                        }

                        @Override
                        public void Failure(String failure) {
                            setAlerttDialog(StringUtil.WARNING, failure);
                        }
                    });
                }
            }
        });
    }

    private void updateUser(String userName, String password, String name, String check, String id) {
        new RestPresenter().updateUser(userName, password, name, check, id, new RestCallback() {
            @Override
            public void Success(QuerySnapshot task) {
                setAlerttDialog(StringUtil.WARNING, name + " Berhasil di update");
                getDialog().dismiss();
            }

            @Override
            public void Failed(String failed) {
                setLog(failed);
                setAlerttDialog(StringUtil.WARNING, failed);
            }

            @Override
            public void Failure(String failure) {
                setLog(failure);
                setAlerttDialog(StringUtil.WARNING, failure);
            }
        });
    }
}
