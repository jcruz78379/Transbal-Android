package com.example.transmobile.Reimpresion.Modelos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mReimpresion  extends RealmObject {
    @PrimaryKey
    private int id_mReimpresion;
    private String id_patio;
    private String patio;
    private String id_cliente;
    private String cliente;
    private String chasis;
    private String usado;
    private String placa;
    private String usuario;
    private String fecha_Imprecion;

    public mReimpresion() {
    }

    public mReimpresion(String id_patio, String patio, String id_cliente, String cliente, String chasis, String usado, String placa, String usuario, String fecha_Imprecion) {
        this.id_patio = id_patio;
        this.patio = patio;
        this.id_cliente = id_cliente;
        this.cliente = cliente;
        this.chasis = chasis;
        this.usado = usado;
        this.placa = placa;
        this.usuario = usuario;
        this.fecha_Imprecion = fecha_Imprecion;
    }

    public int getId_mReimpresion() {
        return id_mReimpresion;
    }

    public void setId_mReimpresion(int id_mReimpresion) {
        this.id_mReimpresion = id_mReimpresion;
    }

    public String getId_patio() {
        return id_patio;
    }

    public void setId_patio(String id_patio) {
        this.id_patio = id_patio;
    }

    public String getPatio() {
        return patio;
    }

    public void setPatio(String patio) {
        this.patio = patio;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getUsado() {
        return usado;
    }

    public void setUsado(String usado) {
        this.usado = usado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_Imprecion() {
        return fecha_Imprecion;
    }

    public void setFecha_Imprecion(String fecha_Imprecion) {
        this.fecha_Imprecion = fecha_Imprecion;
    }
}
