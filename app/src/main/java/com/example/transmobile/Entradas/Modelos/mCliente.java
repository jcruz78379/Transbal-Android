package com.example.transmobile.Entradas.Modelos;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mCliente extends RealmObject {
    @PrimaryKey
    private int id_mcliente;// simplemnete es un autoincremantable
    private String id_cliente;
    private String nombreClinete;

    public mCliente(){}


    public mCliente(String id_cliente, String nombreClinete) {
        this.id_cliente = id_cliente;
        this.nombreClinete = nombreClinete;
    }

    public int getId_mcliente() {
        return id_mcliente;
    }

    public void setId_mcliente(int id_mcliente) {
        this.id_mcliente = id_mcliente;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombreClinete() {
        return nombreClinete;
    }

    public void setNombreClinete(String nombreClinete) {
        this.nombreClinete = nombreClinete;
    }
}
