package com.example.transmobile.Entradas.Modelos;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class mEntrada extends RealmObject {

    @PrimaryKey
    private int id_mEntrada;
    private String id_clinete;
    private String chasis;
    private String licenciaConductor;
    private String cedulaConductor;
    private String nombreCondutor;
    private String id_usuario;
    private String fecha;
    private String obrevaciones;
    private String id_patio;
    private String gasolina;
    private String kilometraje;
    private String id_modelo;
    private String id_color;
    private String impresa;

    public mEntrada() {
    }


    public mEntrada(int id_mEntrada) {
        this.id_mEntrada = id_mEntrada;
    }

    public mEntrada(String id_clinete, String chasis, String licenciaConductor, String cedulaConductor, String nombreCondutor, String id_usuario, String fecha, String obrevaciones, String id_patio, String gasolina, String kilometraje, String id_modelo, String id_color, String impresa) {
        this.id_clinete = id_clinete;
        this.chasis = chasis;
        this.licenciaConductor = licenciaConductor;
        this.cedulaConductor = cedulaConductor;
        this.nombreCondutor = nombreCondutor;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.obrevaciones = obrevaciones;
        this.id_patio = id_patio;
        this.gasolina = gasolina;
        this.kilometraje = kilometraje;
        this.id_modelo = id_modelo;
        this.id_color = id_color;
        this.impresa = impresa;
    }

    public int getId_mEntrada() {
        return id_mEntrada;
    }

    public void setId_mEntrada(int id_mEntrada) {
        this.id_mEntrada = id_mEntrada;
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

    public String getLicenciaConductor() {
        return licenciaConductor;
    }

    public void setLicenciaConductor(String licenciaConductor) {
        this.licenciaConductor = licenciaConductor;
    }

    public String getCedulaConductor() {
        return cedulaConductor;
    }

    public void setCedulaConductor(String cedulaConductor) {
        this.cedulaConductor = cedulaConductor;
    }

    public String getNombreCondutor() {
        return nombreCondutor;
    }

    public void setNombreCondutor(String nombreCondutor) {
        this.nombreCondutor = nombreCondutor;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObrevaciones() {
        return obrevaciones;
    }

    public void setObrevaciones(String obrevaciones) {
        this.obrevaciones = obrevaciones;
    }

    public String getId_patio() {
        return id_patio;
    }

    public void setId_patio(String id_patio) {
        this.id_patio = id_patio;
    }

    public String getGasolina() {
        return gasolina;
    }

    public void setGasolina(String gasolina) {
        this.gasolina = gasolina;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(String id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getId_color() {
        return id_color;
    }

    public void setId_color(String id_color) {
        this.id_color = id_color;
    }

    public String getImpresa() {
        return impresa;
    }

    public void setImpresa(String impresa) {
        this.impresa = impresa;
    }
}
