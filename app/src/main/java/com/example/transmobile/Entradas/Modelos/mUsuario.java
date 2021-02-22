package com.example.transmobile.Entradas.Modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mUsuario extends RealmObject {

    @PrimaryKey
    private int id_mUsuario;
    private String id_usuario;
    private String username;
    private String pass;

    public mUsuario() {
    }

    public mUsuario( String id_usuario, String username, String pass) {

        this.id_usuario = id_usuario;
        this.username = username;
        this.pass = pass;
    }

    public int getId_mUsuario() {
        return id_mUsuario;
    }

    public void setId_mUsuario(int id_mUsuario) {
        this.id_mUsuario = id_mUsuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
