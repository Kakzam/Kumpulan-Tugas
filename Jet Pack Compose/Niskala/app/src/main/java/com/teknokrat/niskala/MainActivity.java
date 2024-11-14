package com.teknokrat.niskala;

import android.os.Bundle;

import com.teknokrat.niskala.databinding.ActivityMainBinding;
import com.teknokrat.niskala.dll.Base;
import com.teknokrat.niskala.menu.DashboardActivity;
import com.teknokrat.niskala.menu.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Base {

    private ActivityMainBinding binding;
    private List<Integer> list = new ArrayList<>();
    private Integer index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLog("===============  " + getClass().getSimpleName() + " ===============");
        if (getL()){
            setIntent(DashboardActivity.class);
        } else if (getPertama()){
            setIntent(LoginActivity.class);
        } else {
            list.add(R.drawable.ic_screen_1);
            list.add(R.drawable.ic_screen_2);
            list.add(R.drawable.ic_screen_3);
            binding.gambarScreen.setImageResource(list.get(index));
            binding.buttonNext.setOnClickListener(view -> {
                index++;
                if (index > 2) {
                    setPertama(true);
                    setIntent(LoginActivity.class);
                }
                else binding.gambarScreen.setImageResource(list.get(index));
            });
        }
    }
}