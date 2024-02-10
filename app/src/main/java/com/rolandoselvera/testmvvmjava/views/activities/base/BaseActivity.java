package com.rolandoselvera.testmvvmjava.views.activities.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.views.components.DialogScreenProgress;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;
    private DialogScreenProgress dialogProgress;
    private MaterialAlertDialogBuilder dialog;
    private ActionBar actionBar;

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initializeView();

    protected abstract void initComponents();

    protected abstract void initViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutRes() != 0) {
            binding = DataBindingUtil.setContentView(this, getLayoutRes());
        }

        dialog = new MaterialAlertDialogBuilder(this);

        initViewModel();
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

    protected void hideProgress() {
        if (null != dialogProgress && dialogProgress.isShowing()) {
            dialogProgress.cancel();
        }
    }

    public void showAlert(String title, String message, Runnable onAccept) {
        if (dialog != null) {
            dialog.setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(BaseActivity.this.getString(R.string.accept), (dialogInterface, which) -> onAccept.run())
                    .setOnDismissListener(DialogInterface::dismiss)
                    .show();
        }
    }

    public void showAlert(String title, String message, Runnable onAccept, Runnable onCancel) {
        if (dialog != null) {
            dialog.setTitle(title)
                    .setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.accept), (dialogInterface, which) -> onAccept.run())
                    .setNegativeButton(getString(R.string.cancel), (dialogInterface, which) -> onCancel.run())
                    .setOnDismissListener(DialogInterface::dismiss)
                    .show();
        }
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
