package com.ayamgeprek.agh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Handle
         * https://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
         * */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), SelectActivity.class));
                finish();
            }
        }, 4000);
    }
}