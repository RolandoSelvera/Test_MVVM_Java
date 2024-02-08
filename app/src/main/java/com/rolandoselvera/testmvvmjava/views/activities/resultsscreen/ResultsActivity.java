package com.rolandoselvera.testmvvmjava.views.activities.resultsscreen;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.databinding.ActivityResultsBinding;
import com.rolandoselvera.testmvvmjava.views.activities.base.BaseActivity;

public class ResultsActivity extends BaseActivity<ActivityResultsBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_results;
    }

    @Override
    protected void initializeView() {
        setToolbarTitle(getString(R.string.catalog));
    }

    @Override
    protected void initComponents() {

    }
}