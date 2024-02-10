package com.rolandoselvera.testmvvmjava.data.api;

import com.rolandoselvera.testmvvmjava.data.models.Abastecimiento;
import com.rolandoselvera.testmvvmjava.data.models.interfaces.ResultApiClient;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ResultService {

    private ResultApiClient resultApiClient;

    public ResultService() {
        Retrofit retrofit = RetrofitHelper.getRetrofit();
        this.resultApiClient = retrofit.create(ResultApiClient.class);
    }

    public Single<Abastecimiento> getResults() {
        return resultApiClient.getAllProducts()
                .subscribeOn(Schedulers.io())
                .map(abastecimiento -> {
                    if (abastecimiento != null) {
                        return abastecimiento;
                    } else {
                        return null;
                    }
                });
    }
}

