package com.rolandoselvera.testmvvmjava.data.models;

import java.util.List;

public class Abastecimiento {
    private List<SanitAbastecimiento> sanitAbastecimiento;
    private long success;
    private String message;

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

    public void setMessage(String value) {
        this.message = value;
    }
}