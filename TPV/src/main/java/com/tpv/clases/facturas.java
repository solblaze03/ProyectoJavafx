package com.tpv.clases;

import java.sql.Timestamp;

public class facturas {
    private String trabajador;
    private String tipopago;
    private Timestamp horacompra;
    private String producto;
    private int cantidad;
    private double precio;
    private int descuento;
    private double total;

    public Timestamp getHoracompra() {
        return horacompra;
    }

    public facturas(String trabajador, String tipopago, Timestamp horacompra, String producto, int cantidad, double precio, int descuento, double total) {
        this.trabajador = trabajador;
        this.tipopago = tipopago;
        this.horacompra = horacompra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.total = total;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public String getTipopago() {
        return tipopago;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }
}
