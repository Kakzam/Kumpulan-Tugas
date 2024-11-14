package com.mobile.skud_id.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobile.skud_id.databinding.FragmentLoadingBinding;

public class LoadingFragment extends BottomSheetDialogFragment {

    Context context;
    FragmentLoadingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoadingBinding.inflate(getLayoutInflater());
        context = getContext();
        binding.fragmentLoadingText.setText(new Preference(context).getLoading());
        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (new Preference(context).getLoading().equals(new BaseActivity().EMPTY)) {
            dialog.dismiss();
        } else {
            new LoadingFragment().show(getActivity().getSupportFragmentManager(), "LoadingFragment");
        }
    }
}