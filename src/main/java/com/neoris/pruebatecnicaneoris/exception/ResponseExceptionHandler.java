package com.neoris.pruebatecnicaneoris.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * Clase que maneja las diferentes excepciones en el aplicativo
 */
@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /*
     * Método respuesta para la excepción BussinesLogic
     */
    @ExceptionHandler(value = { BussinesLogicException.class })
    public final ResponseEntity<Object> filtroBussinesLogicException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Object error = String.valueOf(status.value())+ status.name() +" \n"+ ex.getMessage() + "\n"+
                request.getDescription(false);
        System.out.println(ex.getStackTrace());
        return new ResponseEntity<Object>(error, status);

    }

    /*
     * Método respuesta para la excepción ResourceNotFoundException
     */
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public final ResponseEntity<Object> filtroResourceNotFoundException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Object error = String.valueOf(status.value())+ status.name() +" \n"+ ex.getMessage() + "\n"+
                request.getDescription(false);
        System.out.println(ex.getStackTrace());
        return new ResponseEntity<Object>(error, status);

    }

    /*
     * Método que permite dar una respuesta al cliente cuando se presenta una excepción en el programa
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errores = new ArrayList<String>();
        for (FieldError errorDato : ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()) {
            errores.add(errorDato.getDefaultMessage());
        }
        for (ObjectError errorDato : ((MethodArgumentNotValidException) ex).getBindingResult().getGlobalErrors()) {
            errores.add(errorDato.getDefaultMessage());
        }
        String error =String.valueOf(status.value()) + status.name() + errores;
        System.out.println(ex.getStackTrace());
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
}
