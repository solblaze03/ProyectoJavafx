package com.tpv.clases;

public class Productos {
    private String codigosbarra;
    private String nombre;
    private String id_categoria;
    private String urlbarras;
    private int iva;
    private int dcto;
    private double precio;


    public Productos(String codigosbarra, String nombre, String id_categoria, String urlbarras, int iva, int dcto,double precio) {
        this.codigosbarra = codigosbarra;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.urlbarras = urlbarras;
        this.iva = iva;
        this.dcto = dcto;
        this.precio = precio;
    }



    public Productos(String urlbarras) {
        this.urlbarras = urlbarras;
    }

    public String getCodigosbarra() {
        return codigosbarra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public int getIva() {
        return iva;
    }

    public String getUrlbarras() {
        return urlbarras;
    }

    public int getDcto() {
        return dcto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCodigosbarra(String codigosbarra) {
        this.codigosbarra = codigosbarra;
    }
}
