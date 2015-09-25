package com.rocdev.android.takenlijst;

/**
 * Created by piet on 23-09-15.
 */
public class Taak {
    private int taakId;
    private int lijstId;
    private String naam;
    private String notitie;
    private String afgerond;
    private String verborgen;

    public Taak() {
        naam = "";
        notitie = "";
        afgerond = "";
        verborgen = "";
    }

    public Taak(int lijstId, String naam, String notitie, String afgerond, String verborgen) {
        this.lijstId = lijstId;
        this.naam = naam;
        this.notitie = notitie;
        this.afgerond = afgerond;
        this.verborgen = verborgen;
    }

    public Taak(int taakId, int lijstId, String naam, String notitie, String afgerond,
                String verborgen) {
        this.taakId = taakId;
        this.lijstId = lijstId;
        this.naam = naam;
        this.notitie = notitie;
        this.afgerond = afgerond;
        this.verborgen = verborgen;
    }

    public int getTaakId() {
        return taakId;
    }

    public void setTaakId(int taakId) {
        this.taakId = taakId;
    }

    public int getLijstId() {
        return lijstId;
    }

    public void setLijstId(int lijstId) {
        this.lijstId = lijstId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNotitie() {
        return notitie;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
    }

    public String getAfgerond() {
        return afgerond;
    }

    public void setAfgerond(String afgerond) {
        this.afgerond = afgerond;
    }

    public String getVerborgen() {
        return verborgen;
    }

    public void setVerborgen(String verborgen) {
        this.verborgen = verborgen;
    }
}
