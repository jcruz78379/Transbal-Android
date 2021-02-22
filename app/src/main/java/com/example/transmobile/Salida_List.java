package com.example.transmobile;

public class Salida_List {

    private String id;
    private String ANUMCLI;
    private String ANUMMOTOR;
    private String ACHASIS;
    private String COLOR;
    private String DESCRIP1;


    public Salida_List(String id, String ANUMCLI, String ANUMMOTOR, String ACHASIS, String COLOR, String DESCRIP1) {
        this.id = id;
        this.ANUMCLI = ANUMCLI;
        this.ANUMMOTOR = ANUMMOTOR;
        this.ACHASIS = ACHASIS;
        this.COLOR = COLOR;
        this.DESCRIP1 = DESCRIP1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getANUMCLI() {
        return ANUMCLI;
    }

    public void setANUMCLI(String ANUMCLI) {
        this.ANUMCLI = ANUMCLI;
    }

    public String getANUMMOTOR() {
        return ANUMMOTOR;
    }

    public void setANUMMOTOR(String ANUMMOTOR) {
        this.ANUMMOTOR = ANUMMOTOR;
    }

    public String getACHASIS() {
        return ACHASIS;
    }

    public void setACHASIS(String ACHASIS) {
        this.ACHASIS = ACHASIS;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getDESCRIP1() {
        return DESCRIP1;
    }

    public void setDESCRIP1(String DESCRIP1) {
        this.DESCRIP1 = DESCRIP1;
    }
}
