package com.tpvprincipal.clases;

public class Productos {
    private String codigosbarra;
    private String nombre;
    private String id_categoria;
    private String urlbarras;
    private int iva;
    private int dcto;
    private double precio;

    private double total;
    private int cantidad;

    public double getTotal() {
        return total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Productos(String codigosbarra, String nombre, String id_categoria, String urlbarras, int iva, int dcto, double precio) {
        this.codigosbarra = codigosbarra;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.urlbarras = urlbarras;
        this.iva = iva;
        this.dcto = dcto;
        this.precio = precio;
    }

    public Productos(String codigosbarra, String nombre, String id_categoria, String urlbarras, int iva, int dcto, double precio, double total, int cantidad) {
        this.codigosbarra = codigosbarra;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.urlbarras = urlbarras;
        this.iva = iva;
        this.dcto = dcto;
        this.precio = precio;
        this.total = total;
        this.cantidad = cantidad;
    }


    public void setTotal(double total) {
        this.total += total;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
