package com.rolandoselvera.testmvvmjava.data.models;

public class SanitAbastecimiento {
    private long idAbastecimiento;
    private String tipoAbastecimiento;
    private String usuarioCreacion;
    private String usuarioModificacion;
    private String usuarioEliminacion;
    private String fechaCreacion;
    private String fechaModificacion;
    private String fechaEliminacion;
    private String estatusAbastecimiento;

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
}
