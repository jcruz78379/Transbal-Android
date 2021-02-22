package com.example.transmobile.Salida.Modelos;

public class ModeloPatio {
    private String id;
    private String patio;
    private String accesspatio;

    public ModeloPatio(String id, String patio, String accesspatio) {
        this.id = id;
        this.patio = patio;
        this.accesspatio = accesspatio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatio() {
        return patio;
    }

    public void setPatio(String patio) {
        this.patio = patio;
    }

    public String getAccesspatio() {
        return accesspatio;
    }

    public void setAccesspatio(String accesspatio) {
        this.accesspatio = accesspatio;
    }
}
