package com.rolandoselvera.testmvvmjava.views.activities.downloadscreen;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.databinding.ActivityDownloadBinding;
import com.rolandoselvera.testmvvmjava.views.activities.base.BaseActivity;

public class DownloadActivity extends BaseActivity<ActivityDownloadBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_download;
    }

    @Override
    protected void initializeView() {
        setToolbarTitle(getString(R.string.download));
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initViewModel() {

    }
}