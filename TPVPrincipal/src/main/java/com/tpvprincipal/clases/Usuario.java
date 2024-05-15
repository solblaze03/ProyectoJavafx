package com.tpvprincipal.clases;

public class Usuario {
    private String DNI;
    private String nombre;
    private String privilegios;
    private String urlImagen;
    private String password;

    public Usuario(String DNI, String nombre, String privilegios) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.privilegios = privilegios;
    }

    public Usuario(){

    }

    public Usuario(String DNI, String nombre, String privilegios, String urlImagen,String password) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.privilegios = privilegios;
        this.urlImagen = urlImagen;
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public void añadirUsuario(String numdni, String nombre, String privilegios, String urlImagen,String password) {
        this.DNI = numdni;
        this.nombre = nombre;
        this.privilegios = privilegios;
        this.urlImagen = urlImagen;
        this.password = password;
    }

}
