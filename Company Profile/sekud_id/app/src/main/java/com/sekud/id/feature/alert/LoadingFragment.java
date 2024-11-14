package com.sekud.id.feature.alert;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sekud.id.R;
import com.sekud.id.base.BaseActivity;
import com.sekud.id.base.Preference;

public class LoadingFragment extends BottomSheetDialogFragment {

    View view;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loading, container, false);
        context = getContext();
        TextView textView = view.findViewById(R.id.fragment_loading_text);
        textView.setText(new Preference(context).getLoading());
        return view;
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