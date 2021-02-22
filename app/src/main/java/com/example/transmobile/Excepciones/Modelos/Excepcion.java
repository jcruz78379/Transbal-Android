package com.example.transmobile.Excepciones.Modelos;

public class Excepcion {
    private String id;
    private String nombre;
    private String descripcion;
    private String fotos;
    private String comentario;
    private String estado;
    private Boolean ckeck;


    public Excepcion(String id, String nombre, String descripcion, String fotos, String comentario, String estado, Boolean ckeck) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotos = fotos;
        this.comentario = comentario;
        this.estado = estado;
        this.ckeck = ckeck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getCkeck() {
        return ckeck;
    }

    public void setCkeck(Boolean ckeck) {
        this.ckeck = ckeck;
    }
}
