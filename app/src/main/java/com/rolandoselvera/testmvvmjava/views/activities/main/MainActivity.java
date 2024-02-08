package com.rolandoselvera.testmvvmjava.views.activities.main;

import android.content.Intent;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.databinding.ActivityMainBinding;
import com.rolandoselvera.testmvvmjava.views.activities.base.BaseActivity;
import com.rolandoselvera.testmvvmjava.views.activities.downloadscreen.DownloadActivity;
import com.rolandoselvera.testmvvmjava.views.activities.resultsscreen.ResultsActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void initComponents() {
        binding.buttonDownload.setOnClickListener(v -> {
            startActivity(new Intent(this, DownloadActivity.class));
        });

        binding.buttonShowResults.setOnClickListener(v -> {
            startActivity(new Intent(this, ResultsActivity.class));
        });
    }
}