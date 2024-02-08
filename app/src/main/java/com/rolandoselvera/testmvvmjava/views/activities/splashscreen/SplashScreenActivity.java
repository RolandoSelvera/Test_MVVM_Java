package com.rolandoselvera.testmvvmjava.views.activities.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.viewmodels.splashviewmodel.SplashViewModel;
import com.rolandoselvera.testmvvmjava.views.activities.main.MainActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        observeSplashLiveData();
    }

    private void observeSplashLiveData() {
        SplashViewModel splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        splashViewModel.startDelaySplashScreen();

        splashViewModel.getLiveData().observe(this, aVoid -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        });
    }
}
