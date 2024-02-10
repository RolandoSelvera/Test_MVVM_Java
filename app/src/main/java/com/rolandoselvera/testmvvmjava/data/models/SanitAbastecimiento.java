package com.rolandoselvera.testmvvmjava.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanitAbastecimiento implements Serializable {

    @SerializedName("idAbastecimiento")
    private long idAbastecimiento;

    @SerializedName("tipoAbastecimiento")
    private String tipoAbastecimiento;

    @SerializedName("usuarioCreacion")
    private String usuarioCreacion;

    @SerializedName("usuarioModificacion")
    private String usuarioModificacion;

    @SerializedName("usuarioEliminacion")
    private String usuarioEliminacion;

    @SerializedName("fechaCreacion")
    private String fechaCreacion;

    @SerializedName("fechaModificacion")
    private String fechaModificacion;

    @SerializedName("fechaEliminacion")
    private String fechaEliminacion;

    private String estatusAbastecimiento;

    private boolean isSelected;

    public long getIDAbastecimiento() {
        return idAbastecimiento;
    }

    public void setIDAbastecimiento(long value) {
        this.idAbastecimiento = value;
    }

    public String getTipoAbastecimiento() {
        return tipoAbastecimiento;
    }

    public void setTipoAbastecimiento(String value) {
        this.tipoAbastecimiento = value;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String value) {
        this.usuarioCreacion = value;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String value) {
        this.usuarioModificacion = value;
    }

    public String getUsuarioEliminacion() {
        return usuarioEliminacion;
    }

    public void setUsuarioEliminacion(String value) {
        this.usuarioEliminacion = value;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String value) {
        this.fechaCreacion = value;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String value) {
        this.fechaModificacion = value;
    }

    public String getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(String value) {
        this.fechaEliminacion = value;
    }

    public String getEstatusAbastecimiento() {
        return estatusAbastecimiento;
    }

    public void setEstatusAbastecimiento(String estatusAbastecimiento) {
        this.estatusAbastecimiento = estatusAbastecimiento;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
