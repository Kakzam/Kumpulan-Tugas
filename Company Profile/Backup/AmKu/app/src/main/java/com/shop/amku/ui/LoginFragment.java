package com.shop.amku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shop.amku.DashboardActivity;
import com.shop.amku.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding login;
    Boolean isLogin = false;

    String id = "";
    String permission = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        login = FragmentLoginBinding.inflate(getLayoutInflater());
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        login.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.bar.setVisibility(View.VISIBLE);
                if (!login.username.getText().toString().isEmpty()
                        && !login.password.getText().toString().isEmpty()) {
                    firestore.collection(RegisterFragment.REGISTER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot d : task.getResult()) {
                                    if (d.get(RegisterFragment.USERNAME).toString().equals(login.username.getText().toString())
                                            && d.get(RegisterFragment.PASSWORD).toString().equals(login.password.getText().toString())
                                    ) {
                                        id = d.getId();
                                        permission = d.get(RegisterFragment.PERMISSION).toString();
                                        isLogin = true;
                                    }
                                }

                                if (isLogin) {
                                    login.bar.setVisibility(View.GONE);
                                    Intent intent = new Intent(getContext(), DashboardActivity.class);
                                    intent.putExtra(DashboardActivity.ID, id);
                                    intent.putExtra(DashboardActivity.PERMISSION, permission);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    login.bar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Silahkan periksa kembali username, dan password anda.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                login.bar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    login.bar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Silahkan periksa kembali username, dan password anda.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return login.getRoot();
    }
}