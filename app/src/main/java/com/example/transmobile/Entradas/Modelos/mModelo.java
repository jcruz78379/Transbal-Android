package com.example.transmobile.Entradas.Modelos;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mModelo extends RealmObject {
    @PrimaryKey
    private int id_mModelo;
    private String id_modelo;
    private String nombreModelo;
    private String id_color;
    private String id_cliente;

    public mModelo() {}

    public mModelo(String id_modelo, String nombreModelo, String id_color, String id_cliente) {
        this.id_modelo = id_modelo;
        this.nombreModelo = nombreModelo;
        this.id_color = id_color;
        this.id_cliente = id_cliente;
    }

    public int getId_mModelo() {
        return id_mModelo;
    }

    public void setId_mModelo(int id_mModelo) {
        this.id_mModelo = id_mModelo;
    }

    public String getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(String id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getId_color() {
        return id_color;
    }

    public void setId_color(String id_color) {
        this.id_color = id_color;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }
}
