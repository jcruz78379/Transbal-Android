package com.example.transmobile.Entradas.Modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mPatio extends RealmObject {

    @PrimaryKey
    private int id_mPatio;// simplemnete es un autoincremantable
    private String id_Patio;
    private String nombrePatio;

    public mPatio() {}

    public mPatio(String id_Patio, String nombrePatio) {
        this.id_mPatio = id_mPatio;
        this.id_Patio = id_Patio;
        this.nombrePatio = nombrePatio;
    }

    public int getId_mPatio() {
        return id_mPatio;
    }

    public void setId_mPatio(int id_mPatio) {
        this.id_mPatio = id_mPatio;
    }

    public String getId_Patio() {
        return id_Patio;
    }

    public void setId_Patio(String id_Patio) {
        this.id_Patio = id_Patio;
    }

    public String getNombrePatio() {
        return nombrePatio;
    }

    public void setNombrePatio(String nombrePatio) {
        this.nombrePatio = nombrePatio;
    }
}
