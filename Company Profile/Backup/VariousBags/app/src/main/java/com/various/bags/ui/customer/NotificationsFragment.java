package com.various.bags.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.various.bags.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

}