package com.example.proyecto;

public class Alumno {
    private String nc;
    private String n;
    private String pAp;
    private String sAp;
    private String s;
    private String c;
    private String f;
    private String t;

    public Alumno(String nc, String n, String pAp, String sAp, String s, String c, String f, String t) {
        this.nc = nc;
        this.n = n;
        this.pAp = pAp;
        this.sAp = sAp;
        this.s = s;
        this.c = c;
        this.f = f;
        this.t = t;

        // Getters

    }

    public String getNc() {
        return nc;
    }

    public String getN() {
        return n;
    }

    public String getpAp() {
        return pAp;
    }

    public String getsAp() {
        return sAp;
    }

    public String getS() {
        return s;
    }

    public String getC() {
        return c;
    }

    public String getF() {
        return f;
    }

    public String getT() {
        return t;
    }
}