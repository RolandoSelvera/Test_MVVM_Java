package com.rolandoselvera.testmvvmjava.viewmodels.downloadviewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rolandoselvera.testmvvmjava.data.models.Abastecimiento;
import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;
import com.rolandoselvera.testmvvmjava.data.models.enums.ResultStatus;
import com.rolandoselvera.testmvvmjava.data.repository.ProductsRepository;
import com.rolandoselvera.testmvvmjava.data.repository.ResultsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class DownloadViewModel extends ViewModel {
    private ResultsRepository resultsRepository;
    private ProductsRepository productsRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<Abastecimiento> supplying = new MutableLiveData<>();

    public LiveData<Abastecimiento> getSupplying() {
        return supplying;
    }

    private MutableLiveData<Boolean> hasInserted = new MutableLiveData<>();

    public LiveData<Boolean> hasInserted() {
        return hasInserted;
    }

    private MutableLiveData<Boolean> hasDeleted = new MutableLiveData<>();

    public LiveData<Boolean> hasDeleted() {
        return hasDeleted;
    }

    private MutableLiveData<Boolean> mLoader = new MutableLiveData<>(true);

    public LiveData<Boolean> loader() {
        return mLoader;
    }

    public DownloadViewModel(ResultsRepository resultsRepository, ProductsRepository productsRepository) {
        this.resultsRepository = resultsRepository;
        this.productsRepository = productsRepository;
    }

    @SuppressLint("CheckResult")
    public void getProducts() {
        mLoader.setValue(true);
        resultsRepository.getResults()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        supplying.setValue(new Abastecimiento(ResultStatus.SUCCESS, response.getSanitAbastecimiento(), response.getSuccess(), response.getMessage()));
                    } else {
                        supplying.setValue(new Abastecimiento(ResultStatus.FAILURE, null, response.getSuccess(), "Intente de nuevo más tarde."));
                    }
                    mLoader.setValue(false);
                }, throwable -> {
                    supplying.setValue(new Abastecimiento(ResultStatus.ERROR, null, 400, throwable.getMessage()));
                    mLoader.setValue(false);
                    Log.e("RESPONSE_ERROR: ", throwable.getMessage());
                });
    }

    public void insertProduct(List<SanitAbastecimiento> productList) {
        mLoader.setValue(true);
        compositeDisposable.add(
                productsRepository.insertProductsToDB(productList)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(status -> {
                                    if (status) {
                                        hasInserted.setValue(status);
                                    } else {
                                        hasInserted.setValue(false);
                                    }
                                },
                                throwable -> {
                                    hasInserted.setValue(null);
                                }
                        )
        );
    }

    public void deleteAllProducts() {
        compositeDisposable.add(
                productsRepository.deleteAllProducts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(deletionSuccess -> {
                                    if (deletionSuccess) {
                                        hasDeleted.setValue(deletionSuccess);
                                    } else {
                                        hasDeleted.setValue(false);
                                    }
                                },
                                throwable -> {
                                    hasDeleted.setValue(null);
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
