package com.example.transmobile.Inventario.Modelo;

public class mItemInventario {

    private String chasis;
    private String descripcion;
    private String fecha;

    public mItemInventario(String chasis, String descripcion, String fecha) {
        this.chasis = chasis;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
