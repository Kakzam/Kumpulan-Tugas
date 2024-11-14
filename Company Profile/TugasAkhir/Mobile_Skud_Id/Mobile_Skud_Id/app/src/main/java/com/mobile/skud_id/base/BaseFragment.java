package com.mobile.skud_id.base;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.skud_id.databinding.FragmentAccountBinding;
import com.mobile.skud_id.network.ReloadCallback;

public class BaseFragment extends Fragment {

    public String INTENT_1 = "INTENT_1";
    public String INTENT_2 = "INTENT_2";

    public Context context;
    public String EMPTY = "EMPTY";
    public View view;

    public LoadingFragment loadingFragment;
    public FragmentActivity activity;

    public void setAlerttDialogReload(ReloadCallback callback) {
        new AlertDialog.Builder(context)
                .setTitle(Html.fromHtml(StringUtil.WARNING))
                .setMessage(Html.fromHtml(StringUtil.RELOAD))
                .setCancelable(false)
                .setNeutralButton(StringUtil.YES, (dialog, id) -> {
                    callback.Success();
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    public void setAlerttDialog(String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(Html.fromHtml(title))
                .setMessage(Html.fromHtml(message))
                .setNeutralButton(StringUtil.YES, (dialog, id) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    public void setAlerttDialogForce(String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(Html.fromHtml(title))
                .setMessage(Html.fromHtml(message))
                .setCancelable(false)
                .create()
                .show();
    }

    public void setAlerttDialogExit() {
        new AlertDialog.Builder(context)
                .setTitle(Html.fromHtml(StringUtil.WARNING))
                .setMessage(Html.fromHtml(StringUtil.PUBLIC_MESSAGE_1))
                .setCancelable(false)
                .setNeutralButton(StringUtil.PUBLIC_MESSAGE_1_YES, (dialog, id) -> {
                    activity.finishAffinity();
                })
                .setNegativeButton(StringUtil.NO, ((dialog, id) -> {
                    dialog.dismiss();
                }))
                .create()
                .show();
    }

    public void setLog(String log) {
        Log.v("ZAM", log);
    }

    public void setToast(String toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public void setToastLong(String toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
    }

    public void setLoading(String loading) {
//        new Preference(getContext()).setLoading(loading);
//        loadingFragment.show(getActivity().getSupportFragmentManager(), "LoadingFragment");
    }

    public void dismissLoading() {
//        new Preference(getContext()).setLoading(EMPTY);
//        loadingFragment.dismiss();
    }

    public void setActivity(Class<?> activity1) {
        Intent intent = new Intent(getContext(), activity1);
        startActivity(intent);
        getActivity().finish();
    }

    public void setActivity(Class<?> activity1, String value1, String value2) {
        Intent intent = new Intent(getContext(), activity1);
        intent.putExtra(INTENT_1, value1);
        intent.putExtra(INTENT_2, value2);
        startActivity(intent);
        getActivity().finish();
    }

    public void setActivityNoFinish(Class<?> activity) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
    }
}