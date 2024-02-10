package com.rolandoselvera.testmvvmjava.data.models.interfaces;

import com.rolandoselvera.testmvvmjava.data.models.Abastecimiento;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ResultApiClient {
    @GET("catalogos/Sanit_abastecimiento")
    Single<Abastecimiento> getAllProducts();
}