package com.example.transmobile.Salida.Modelos;

public class ModeloSalida {

    private String id;

    private String autoliquidarID;


    private String motor;


    private String num_certificado;


    private String num_cliente;


    private String nombre_cliente;

    private String chasis;

    private String descripcion;


    private String color;


    private String id_patio;


    private String patio;


    private String axco;


    private String modelo;


    private String num_liquidacion;


    private String num_orden_entrada;


    private String transmitador;


    public ModeloSalida(String id, String autoliquidarID, String motor, String num_certificado, String num_cliente, String nombre_cliente, String chasis, String descripcion, String color, String id_patio, String patio, String axco, String modelo, String num_liquidacion, String num_orden_entrada, String transmitador) {
        this.id = id;
        this.autoliquidarID = autoliquidarID;
        this.motor = motor;
        this.num_certificado = num_certificado;
        this.num_cliente = num_cliente;
        this.nombre_cliente = nombre_cliente;
        this.chasis = chasis;
        this.descripcion = descripcion;
        this.color = color;
        this.id_patio = id_patio;
        this.patio = patio;
        this.axco = axco;
        this.modelo = modelo;
        this.num_liquidacion = num_liquidacion;
        this.num_orden_entrada = num_orden_entrada;
        this.transmitador = transmitador;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutoliquidarID() {
        return autoliquidarID;
    }

    public void setAutoliquidarID(String autoliquidarID) {
        this.autoliquidarID = autoliquidarID;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getNum_certificado() {
        return num_certificado;
    }

    public void setNum_certificado(String num_certificado) {
        this.num_certificado = num_certificado;
    }

    public String getNum_cliente() {
        return num_cliente;
    }

    public void setNum_cliente(String num_cliente) {
        this.num_cliente = num_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getAxco() {
        return axco;
    }

    public void setAxco(String axco) {
        this.axco = axco;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNum_liquidacion() {
        return num_liquidacion;
    }

    public void setNum_liquidacion(String num_liquidacion) {
        this.num_liquidacion = num_liquidacion;
    }

    public String getNum_orden_entrada() {
        return num_orden_entrada;
    }

    public void setNum_orden_entrada(String num_orden_entrada) {
        this.num_orden_entrada = num_orden_entrada;
    }

    public String getTransmitador() {
        return transmitador;
    }

    public void setTransmitador(String transmitador) {
        this.transmitador = transmitador;
    }
}
