package com.sekud.id.feature;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.firestore.DocumentSnapshot;
import com.sekud.id.R;
import com.sekud.id.base.BaseActivity;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.network.BaseConfig;
import com.sekud.id.network.RestPresenter;
import com.sekud.id.network.VersionCallback;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Preference(this).setLogin(true);

        new Handler().postDelayed(() -> {
            new RestPresenter().getVersion(BaseConfig.BASE, BaseConfig.VERSION, new VersionCallback() {

                @Override
                public void Success(DocumentSnapshot task) {
                    setLog("Build Version Server : " + task.get(BaseConfig.VERSION));
                    if (task.get(BaseConfig.VERSION).toString().equals(BaseConfig.VERSION_CODE)) {
                        setActivity(MainActivity.class);
                    } else {
                        setAlerttDialogForce(StringUtil.WARNING, StringUtil.SPLASH_ACTIVITY_MESSAGE_1);
                    }
                }

                @Override
                public void Failed(String failed) {
                    setLog(failed);
                    setActivity(MainActivity.class);
                }

                @Override
                public void Failure(String failure) {
                    setLog(failure);
                    setActivity(MainActivity.class);
                }
            });
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        setAlerttDialogExit();
    }
}