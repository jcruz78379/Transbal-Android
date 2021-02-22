package com.example.transmobile.Entradas.Modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mEntradaReprint extends RealmObject {
    @PrimaryKey
    private int id_mEntradaReprint;
    private String id_clinete;
    private String chasis;
    private String fecha;


    public mEntradaReprint() {
    }

    public mEntradaReprint(int id_mEntradaReprint) {
        this.id_mEntradaReprint = id_mEntradaReprint;
    }

    public mEntradaReprint(String id_clinete, String chasis, String fecha) {
        this.id_clinete = id_clinete;
        this.chasis = chasis;
        this.fecha = fecha;
    }


    public int getId_mEntradaReprint() {
        return id_mEntradaReprint;
    }

    public void setId_mEntradaReprint(int id_mEntradaReprint) {
        this.id_mEntradaReprint = id_mEntradaReprint;
    }

    public String getId_clinete() {
        return id_clinete;
    }

    public void setId_clinete(String id_clinete) {
        this.id_clinete = id_clinete;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}