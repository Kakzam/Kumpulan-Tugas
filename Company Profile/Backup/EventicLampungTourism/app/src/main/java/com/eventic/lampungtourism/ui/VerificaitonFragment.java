package com.eventic.lampungtourism.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eventic.lampungtourism.AdminActivity;
import com.eventic.lampungtourism.CustomerActivity;
import com.eventic.lampungtourism.R;
import com.eventic.lampungtourism.databinding.FragmentVerificaitonBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class VerificaitonFragment extends Fragment {

    FragmentVerificaitonBinding binding;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificaitonBinding.inflate(getLayoutInflater());
        binding.getFullName.setVisibility(View.GONE);
        firestore = FirebaseFirestore.getInstance();
        binding.getFullName.setText("");
        binding.getId.setText("");
        binding.getPassword.setText("");
        binding.createAdmin.setImageDrawable(getActivity().getDrawable(R.drawable.login));

        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.getId.getText().toString().isEmpty() && !binding.getPassword.getText().toString().isEmpty()) {
                    firestore.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    if (documentSnapshot.get("id").toString().equals(binding.getId.getText().toString())
                                            && documentSnapshot.get("password").toString().equals(binding.getPassword.getText().toString())) {
                                        if (Integer.parseInt(documentSnapshot.get("position").toString()) > 5) {
                                            Intent intent = new Intent(getContext(), AdminActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(getContext(), CustomerActivity.class);
                                            startActivity(intent);
                                        }
                                        getActivity().finish();
                                    }
                                }
                            } else
                                Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(getContext(), "Silahkan isi semua dengan benar", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}