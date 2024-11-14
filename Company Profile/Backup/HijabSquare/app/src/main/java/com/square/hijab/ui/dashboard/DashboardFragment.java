package com.square.hijab.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.square.hijab.MainActivity;
import com.square.hijab.MainActivity2;
import com.square.hijab.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.a.getText().toString().equals("admin")
                        && binding.b.getText().toString().equals("admin")
                ) {
                    Intent intent = new Intent(getContext(), MainActivity2.class);
                    startActivity(intent);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}