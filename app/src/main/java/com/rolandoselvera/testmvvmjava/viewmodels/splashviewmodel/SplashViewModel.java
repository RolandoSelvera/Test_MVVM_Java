package com.rolandoselvera.testmvvmjava.viewmodels.splashviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {
    private final MutableLiveData<SplashViewModel> liveData = new MutableLiveData<>();

    public void startDelaySplashScreen() {
        try {
            Thread.sleep(2500);
            updateLiveData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void updateLiveData() {
        SplashViewModel splashViewModel = new SplashViewModel();
        liveData.setValue(splashViewModel);
    }

    public MutableLiveData<SplashViewModel> getLiveData() {
        return liveData;
    }
}
