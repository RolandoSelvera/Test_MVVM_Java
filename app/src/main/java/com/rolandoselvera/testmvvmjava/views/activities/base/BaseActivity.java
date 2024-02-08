package com.rolandoselvera.testmvvmjava.views.activities.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.rolandoselvera.testmvvmjava.views.components.DialogScreenProgress;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;
    private DialogScreenProgress dialogProgress;
    private ActionBar actionBar;

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initializeView();

    protected abstract void initComponents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutRes() != 0) {
            binding = DataBindingUtil.setContentView(this, getLayoutRes());
        }

        initializeView();
        initComponents();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != dialogProgress && dialogProgress.isShowing()) {
            dialogProgress.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void hideKeyboard(View input) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    protected void setToolbarTitle(String title) {
        actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(title);
    }

    protected void showProgress() {
        if (null == dialogProgress) {
            dialogProgress = new DialogScreenProgress(this);
        }
        dialogProgress.show();
    }

    protected void dismissProgress() {
        if (null != dialogProgress && dialogProgress.isShowing()) {
            dialogProgress.cancel();
        }
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
