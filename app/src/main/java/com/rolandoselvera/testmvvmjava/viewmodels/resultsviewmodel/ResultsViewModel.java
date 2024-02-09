package com.rolandoselvera.testmvvmjava.viewmodels.resultsviewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;
import com.rolandoselvera.testmvvmjava.data.repository.ProductsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ResultsViewModel extends ViewModel {

    private ProductsRepository productsRepository;
    private MutableLiveData<List<SanitAbastecimiento>> productsLiveData = new MutableLiveData<>();

    public LiveData<List<SanitAbastecimiento>> getProductsLiveData() {
        return productsLiveData;
    }

    private MutableLiveData<Boolean> mLoader = new MutableLiveData<>(true);

    public LiveData<Boolean> loader() {
        return mLoader;
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ResultsViewModel(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    @SuppressLint("CheckResult")
    public void loadProducts() {
        mLoader.setValue(true);
        productsRepository.getProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsList -> {
                    productsLiveData.setValue(productsList);
                    mLoader.setValue(false);
                }, throwable -> {
                    productsLiveData.setValue(null);
                    mLoader.setValue(false);
                    throwable.printStackTrace();
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        //productsRepository.close();
    }
}
