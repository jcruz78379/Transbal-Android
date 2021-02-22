package com.example.transmobile.Entradas.Modelos;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mColor extends RealmObject {
    @PrimaryKey
    private int id_mColor;
    private String id_color;
    private String nombreColor;

    public mColor(){}

    public mColor(String id_color, String nombreColor) {
        this.id_mColor = id_mColor;
        this.id_color = id_color;
        this.nombreColor = nombreColor;
    }

    public int getId_mColor() {
        return id_mColor;
    }

    public void setId_mColor(int id_mColor) {
        this.id_mColor = id_mColor;
    }

    public String getId_color() {
        return id_color;
    }

    public void setId_color(String id_color) {
        this.id_color = id_color;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }
}
