package com.shop.amku.other;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class Loading {

    ProgressDialog progressDialog;

    public void setLoading(Context context) {
        progressDialog = ProgressDialog.show(context, "Pemberitahuan", "Sedang melakukan proses");
    }

    public void dissmissLoading() {
        progressDialog.dismiss();
    }

}
