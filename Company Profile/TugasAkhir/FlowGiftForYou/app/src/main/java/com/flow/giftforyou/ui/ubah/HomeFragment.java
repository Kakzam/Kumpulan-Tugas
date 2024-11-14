package com.flow.giftforyou.ui.ubah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.flow.giftforyou.BucketActivity;
import com.flow.giftforyou.R;
import com.flow.giftforyou.databinding.FragmentHomeBinding;
import com.flow.giftforyou.ui.UtilString;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.list_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BucketActivity.class).putExtra(UtilString.BUCKET, UtilString.BUCKET).putExtra(UtilString.LOGIN, UtilString.UBAH));
            }
        });

        root.findViewById(R.id.list_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BucketActivity.class).putExtra(UtilString.BUCKET, UtilString.HANTARAN).putExtra(UtilString.LOGIN, UtilString.UBAH));
            }
        });

        root.findViewById(R.id.list_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BucketActivity.class).putExtra(UtilString.BUCKET, UtilString.FRAME).putExtra(UtilString.LOGIN, UtilString.UBAH));
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