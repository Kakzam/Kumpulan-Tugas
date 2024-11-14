package com.catering.dessert.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.catering.dessert.R;
import com.catering.dessert.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterFragment extends Fragment {

    View view;
    ProgressBar progressBar;
    TextInputEditText inputPassword, inputNik, inputAddress, inputPhoneNumber, inputName;
    TextView textRegister, textLogin, textMessage;
    TextInputLayout layoutName;

    Boolean login = false;
    List<DocumentSnapshot> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        inputPassword = view.findViewById(R.id.fragment_register_password);
        inputNik = view.findViewById(R.id.fragment_register_nik);
        inputAddress = view.findViewById(R.id.fragment_register_address);
        inputPhoneNumber = view.findViewById(R.id.fragment_register_handphone);
        inputName = view.findViewById(R.id.fragment_register_name);
        layoutName = view.findViewById(R.id.fragment_register_name_title);
        textRegister = view.findViewById(R.id.fragment_register_button_register);
        textLogin = view.findViewById(R.id.fragment_register_button_login);
        progressBar = view.findViewById(R.id.fragment_register_progressbar);
        textMessage = view.findViewById(R.id.fragment_register_message);

        textLogin.setOnClickListener(view -> {
            if (login) {
                login = false;
                textLogin.setText("Login");
                textRegister.setText("DAFTAR");
                layoutName.setHint("Nama Lengkap");
                inputName.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                inputPhoneNumber.setVisibility(View.VISIBLE);
                inputAddress.setVisibility(View.VISIBLE);
                inputNik.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            list = task.getResult().getDocuments();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            textMessage.setText("Silahkan periksa jaringan anda");
                            textMessage.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                        }
                    }
                });

                login = true;
                textLogin.setText("Registrasi");
                textRegister.setText("Login");
                layoutName.setHint("No. Handphone");
                inputName.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputPhoneNumber.setVisibility(View.GONE);
                inputAddress.setVisibility(View.GONE);
                inputNik.setVisibility(View.GONE);
            }
        });

        textRegister.setOnClickListener(view -> {
            if (login) {
                if (!inputName.getText().toString().isEmpty()
                        && !inputPassword.getText().toString().isEmpty()) {
                    String no = inputName.getText().toString();
                    String password = inputPassword.getText().toString();
                    if (list.size() > 0) {
                        for (DocumentSnapshot document : list) {
                            if (document.get("phone").toString().equals(no) && document.get("password").toString().equals(password)) {
                                new Storage(getContext()).setLogin(true);
                                new Storage(getContext()).setId(document.getId());
                                textMessage.setText("Anda telah berhasil login, silahkan order atau cek order anda");
                                textMessage.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                            }
                        }
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    list = task.getResult().getDocuments();
                                    if (list.size() > 0) {
                                        for (DocumentSnapshot document : list) {
                                            if (document.get("").toString().equals(no) && document.get("").toString().equals(password)) {
                                                new Storage(getContext()).setLogin(true);
                                                new Storage(getContext()).setId(document.getId());
                                            }
                                        }
                                    } else {
                                        textMessage.setText("Silahkan registrasi terlebih dahulu");
                                        textMessage.setVisibility(View.VISIBLE);
                                        new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                                    }
                                } else {
                                    textMessage.setText("Silahkan periksa jaringan anda");
                                    textMessage.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                                }
                            }
                        });
                    }
                } else {
                    textMessage.setText("Pastikan semua sudah terisidengan benar");
                    textMessage.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                }
            } else {
                if (!inputName.getText().toString().isEmpty()
                        && !inputPhoneNumber.getText().toString().isEmpty()
                        && !inputNik.getText().toString().isEmpty()
                        && !inputAddress.getText().toString().isEmpty()
                        && !inputPassword.getText().toString().isEmpty()
                        && inputPhoneNumber.getText().toString().length() > 8) {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", inputName.getText().toString());
                    map.put("phone", inputPhoneNumber.getText().toString());
                    map.put("password", inputPassword.getText().toString());
                    map.put("nik", inputNik.getText().toString());
                    map.put("address", inputAddress.getText().toString());

                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").add(map).addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            textMessage.setText("Registrasi Berhasil");
                            textMessage.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                        } else {
                            textMessage.setText("Silahkan periksa jaringan anda");
                            textMessage.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                        }
                    });
                } else {
                    textMessage.setText("Pastikan semua sudah terisidengan benar");
                    textMessage.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                }
            }
        });

        return view;
    }

}