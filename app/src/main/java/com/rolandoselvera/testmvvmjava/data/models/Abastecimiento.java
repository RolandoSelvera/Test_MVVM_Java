package com.rolandoselvera.testmvvmjava.data.models;

import com.google.gson.annotations.SerializedName;
import com.rolandoselvera.testmvvmjava.data.models.enums.ResultStatus;

import java.io.Serializable;
import java.util.List;

public class Abastecimiento implements Serializable {

    @SerializedName("Sanit_abastecimiento")
    private List<SanitAbastecimiento> sanitAbastecimiento;

    @SerializedName("success")
    private long success;

    @SerializedName("message")
    private String message;

    private ResultStatus status;

    public Abastecimiento(ResultStatus status, List<SanitAbastecimiento> sanitAbastecimiento, long success, String message) {
        this.status = status;
        this.sanitAbastecimiento = sanitAbastecimiento;
        this.success = success;
        this.message = message;
    }

    public List<SanitAbastecimiento> getSanitAbastecimiento() {
        return sanitAbastecimiento;
    }

    public void setSanitAbastecimiento(List<SanitAbastecimiento> value) {
        this.sanitAbastecimiento = value;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long value) {
        this.success = value;
    }

    public String getMessage() {
        return message;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setMessage(String value) {
        this.message = value;
    }
}