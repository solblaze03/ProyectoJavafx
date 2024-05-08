package com.tpv.clases;

public class Categoria {
    private String categoria;
    private String nombre;
    private String url_imagen;

    public String getUrl_imagen() {
        return url_imagen;
    }

    public Categoria(String categoria, String nombre, String url_imagen) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.url_imagen = url_imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }
}
