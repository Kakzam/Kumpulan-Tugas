package com.uber.trip;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class Other {

    ProgressDialog progressDialog;

    public void setLoading(Context context) {
        progressDialog = ProgressDialog.show(context, "Pemberitahuan", "Sedang melakukan proses");
    }

    public void dissmissLoading() {
        progressDialog.dismiss();
    }

    public void setToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
