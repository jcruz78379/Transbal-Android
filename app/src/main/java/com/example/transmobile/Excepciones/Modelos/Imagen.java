package com.example.transmobile.Excepciones.Modelos;

public class Imagen {
    private String name;
    private String ruta;
    private String ID_Excepcion;
    private Boolean guardar;


    public Imagen(String name, String ruta, String ID_Excepcion, Boolean guardar) {
        this.name = name;
        this.ruta = ruta;
        this.ID_Excepcion = ID_Excepcion;
        this.guardar = guardar;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getID_Excepcion() {
        return ID_Excepcion;
    }

    public void setID_Excepcion(String ID_Excepcion) {
        this.ID_Excepcion = ID_Excepcion;
    }

    public Boolean getGuardar() {
        return guardar;
    }

    public void setGuardar(Boolean guardar) {
        this.guardar = guardar;
    }
}



