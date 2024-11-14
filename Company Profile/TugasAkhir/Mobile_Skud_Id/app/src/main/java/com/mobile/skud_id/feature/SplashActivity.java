package com.mobile.skud_id.feature;

import android.os.Bundle;
import android.os.Handler;

import com.mobile.skud_id.R;
import com.mobile.skud_id.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> setActivity(VerificationActivity.class), 4000);
    }
}