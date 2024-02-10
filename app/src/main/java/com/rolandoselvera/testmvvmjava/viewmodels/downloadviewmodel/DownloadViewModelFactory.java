package com.rolandoselvera.testmvvmjava.viewmodels.downloadviewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rolandoselvera.testmvvmjava.data.api.ResultService;
import com.rolandoselvera.testmvvmjava.data.repository.ProductsRepository;
import com.rolandoselvera.testmvvmjava.data.repository.ResultsRepository;

public class DownloadViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public DownloadViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DownloadViewModel.class)) {
            ResultService resultService = new ResultService();
            ResultsRepository resultRepository = new ResultsRepository(resultService);
            ProductsRepository productsRepository = new ProductsRepository(context);
            return (T) new DownloadViewModel(resultRepository, productsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

