package com.neoris.pruebatecnicaneoris.exception;

/*
 * Clase que lanza una excepción y permite tener control cuando un error lógico se presente
 */
public class BussinesLogicException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public BussinesLogicException(String message) {
        super(message);

    }
}
