package com.rolandoselvera.testmvvmjava.views.activities.base;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.views.components.DialogScreenProgress;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;
    private DialogScreenProgress dialogProgress;
    private MaterialAlertDialogBuilder dialog;
    private ActionBar actionBar;

    private Bitmap mBitmap;

    private String currentImagePath;

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 200;

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

    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                toast("Permiso denegado");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (currentImagePath != null) {
                setCurrentImagePath(currentImagePath);
                setmBitmap(BitmapFactory.decodeFile(currentImagePath));
            }
        }
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getCurrentImagePath() {
        return currentImagePath;
    }

    public void setCurrentImagePath(String currentImagePath) {
        this.currentImagePath = currentImagePath;
    }

    private void openCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.rolandoselvera.testmvvmjava.fileprovider",
                        photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentImagePath = imageFile.getAbsolutePath();

        return imageFile;
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
