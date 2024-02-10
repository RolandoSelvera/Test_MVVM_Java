package com.rolandoselvera.testmvvmjava.viewmodels.resultsviewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rolandoselvera.testmvvmjava.data.repository.ProductsRepository;

public class ResultsViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public ResultsViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ResultsViewModel.class)) {
            ProductsRepository productsRepository = new ProductsRepository(context);
            return (T) new ResultsViewModel(productsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}


