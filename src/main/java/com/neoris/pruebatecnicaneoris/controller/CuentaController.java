package com.neoris.pruebatecnicaneoris.controller;

import com.neoris.pruebatecnicaneoris.dto.CuentaDTO;
import com.neoris.pruebatecnicaneoris.entity.Cuenta;
import com.neoris.pruebatecnicaneoris.service.CuentaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Clase que contiene los servicios correspondientes a la entidad Cuenta
 */
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @ApiOperation(value = "Metodo que permite obtener las cuentas de un cliente")
    @GetMapping()
    public ResponseEntity<List<CuentaDTO>> obtenerCuentasCliente(
            @ApiParam(name = "clienteId", value = "Identificador del cliente", required = true) @RequestParam(value = "clienteId", required = true) Long clienteId) {
        try {
            List<CuentaDTO> listaCuentas = cuentaService.obtenerCuentasCliente(clienteId);
            return new ResponseEntity<List<CuentaDTO>>(listaCuentas, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Crea una cuenta de banco para un cliente")
    @PostMapping()
    public ResponseEntity<Object> crearCuentaCliente(
            @RequestBody Cuenta cuenta) {
        try {
            cuentaService.crearCuentaCliente(cuenta);
            return new ResponseEntity<Object>("Cuenta "+ cuenta.getTipo() + " número "+ cuenta.getNumeroCuenta()+ " creada con éxito", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Actualiza la información de una cuenta de banco")
    @PutMapping("{numeroCuenta}")
    public ResponseEntity<Cuenta> actualizarCuentaCliente(@PathVariable(value = "numeroCuenta") Long numeroCuenta,
                                                     @RequestBody Cuenta cuenta) {
        try {
            return ResponseEntity.ok(cuentaService.actualizarCuentaCliente(numeroCuenta, cuenta));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Elimina una cuenta de banco")
    @DeleteMapping("{numeroCuenta}")
    public ResponseEntity<String> eliminarCuentaCliente(@PathVariable(value = "numeroCuenta") Long numeroCuenta) {
        try {
            boolean fueEliminado = cuentaService.eliminarCuentaCliente(numeroCuenta);

            if (!fueEliminado) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Cuenta número "+ numeroCuenta + " eliminada con éxito", HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
