package com.various.bags.ui.administrator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.various.bags.databinding.FragmentConfirmationBinding;

public class ConfirmationFragment extends Fragment {

    private FragmentConfirmationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}