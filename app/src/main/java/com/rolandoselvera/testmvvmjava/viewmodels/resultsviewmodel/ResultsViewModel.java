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

    private MutableLiveData<Boolean> hasUpdated = new MutableLiveData<>();

    public LiveData<Boolean> hasUpdated() {
        return hasUpdated;
    }

    private MutableLiveData<Boolean> imageUpdated = new MutableLiveData<>();

    public LiveData<Boolean> imageUpdated() {
        return imageUpdated;
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

    public void updateProductStatus(long productId, String newStatus) {
        compositeDisposable.add(
                productsRepository.updateProductStatus(productId, newStatus)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                updateSuccess -> {
                                    if (updateSuccess) {
                                        hasUpdated.setValue(updateSuccess);
                                    } else {
                                        hasUpdated.setValue(false);
                                    }
                                },
                                throwable -> {
                                    hasUpdated.setValue(null);
                                }
                        )
        );
    }

    public void updateImagePath(long productId, String imagePath) {
        compositeDisposable.add(
                productsRepository.updateImagePath(productId, imagePath)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                updateSuccess -> {
                                    if (updateSuccess) {
                                        imageUpdated.setValue(updateSuccess);
                                    } else {
                                        imageUpdated.setValue(false);
                                    }
                                },
                                throwable -> {
                                    imageUpdated.setValue(null);
                                }
                        )
        );
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        //productsRepository.close();
    }
}
