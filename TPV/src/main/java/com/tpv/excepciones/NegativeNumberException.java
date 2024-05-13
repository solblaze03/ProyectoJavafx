package com.tpv.excepciones;

public class NegativeNumberException extends  Exception {
    public NegativeNumberException(String mensaje){
        super(mensaje);
    }
}
