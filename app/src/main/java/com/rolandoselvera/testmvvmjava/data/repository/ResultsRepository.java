package com.rolandoselvera.testmvvmjava.data.repository;


import com.rolandoselvera.testmvvmjava.data.api.ResultService;
import com.rolandoselvera.testmvvmjava.data.models.Abastecimiento;

import io.reactivex.Single;

public class ResultsRepository {

    private final ResultService resultService;

    public ResultsRepository(ResultService resultService) {
        this.resultService = resultService;
    }

    public Single<Abastecimiento> getResults() {
        return resultService.getResults();
    }
}




