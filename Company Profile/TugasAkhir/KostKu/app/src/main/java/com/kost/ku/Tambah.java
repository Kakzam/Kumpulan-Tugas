package com.kost.ku;

import android.content.Context;
import android.widget.Toast;

public class Tambah {

    public void ToastLong(String toast, Context context) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }

    public void ToastShort(String toast, Context context) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

}
