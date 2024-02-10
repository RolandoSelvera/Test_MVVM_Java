package com.rolandoselvera.testmvvmjava.views.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.rolandoselvera.testmvvmjava.R;

public class DialogScreenProgress extends Dialog {
    private final Context mContext;

    public DialogScreenProgress(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_screen_progress);
        setCanceledOnTouchOutside(false);

        ProgressBar progressBar = findViewById(R.id.progressIndicator);
        progressBar.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.secondaryColor),
                PorterDuff.Mode.MULTIPLY);

        getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.transparencyBg)));
        setCancelable(false);
    }
}
