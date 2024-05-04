package com.tpv.clases;

public class Usuario {
    private String DNI;
    private String nombre;
    private String privilegios;
    private String urlImagen;

    public Usuario(String DNI, String nombre, String privilegios) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.privilegios = privilegios;
    }
    public Usuario(){

    }
    public void añadirUsuario(String DNI, String nombre, String privilegios, String urlImagen){
        this.DNI = DNI;
        this.nombre = nombre;
        this.privilegios = privilegios;
        this.urlImagen = urlImagen;
    }

    public Usuario(String DNI, String nombre, String privilegios, String urlImagen) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.privilegios = privilegios;
        this.urlImagen = urlImagen;
    }
    @Override
    public String toString(){
        return String.format("%s %s %s %s",this.DNI,this.nombre,this.privilegios,this.urlImagen);
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getDNI() {
        return DNI;
    }

    public String getPrivilegios() {
        return this.privilegios;
    }
}
