package com.neoris.pruebatecnicaneoris.controller;

import com.neoris.pruebatecnicaneoris.dto.EstadoCuentaDTO;
import com.neoris.pruebatecnicaneoris.dto.MovimientoDTO;
import com.neoris.pruebatecnicaneoris.entity.Movimiento;
import com.neoris.pruebatecnicaneoris.service.MovimientoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/*
 * Clase que contiene los servicios correspondientes a la entidad Movimiento
 */
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    MovimientoService movimientoService;

    @ApiOperation(value = "Metodo que permite obtener los movimientos de una cuenta")
    @GetMapping()
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosCuenta(
            @ApiParam(name = "numeroCuenta", value = "Número de la cuenta", required = true) @RequestParam(value = "numeroCuenta", required = true) Long numeroCuenta) {
        try {
            List<MovimientoDTO> listaMovimientos = movimientoService.obtenerMovimientosCuenta(numeroCuenta);
            return new ResponseEntity<List<MovimientoDTO>>(listaMovimientos, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Realiza un movimiento en una cuenta de banco")
    @PostMapping()
    public ResponseEntity<Object> realizarMovimientoCuenta(
            @RequestBody MovimientoDTO movimiento) {
        try {
            movimientoService.realizarMovimientoCuenta(movimiento);
            return new ResponseEntity<Object>("Movimiento realizado con éxito en la cuenta "+ movimiento.getTipo() + " número "+ movimiento.getNumeroCuenta(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Actualiza la información de un movimiento en una cuenta")
    @PutMapping("{id}")
    public ResponseEntity<Movimiento> actualizarMovimientoCuenta(@PathVariable(value = "id") Long id,
                                                          @RequestBody Movimiento movimiento) {
        try {
            return ResponseEntity.ok(movimientoService.actualizarMovimientoCuenta(id, movimiento));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Elimina un movimiento de una cuenta")
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarMovimientoCuenta(@PathVariable(value = "id") Long id) {
        try {
            boolean fueEliminado = movimientoService.eliminarMovimientoCuenta(id);

            if (!fueEliminado) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Movimiento eliminado con éxito", HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Genera un reporte para un cliente en un período de tiempo especificado")
    @GetMapping("/reportes")
    public ResponseEntity<List<EstadoCuentaDTO>> obtenerMovimientosCuenta(
            @ApiParam(name = "clienteId", value = "Identificador de cliente", required = true) @RequestParam(value = "clienteId", required = true) Long clienteId,
            @ApiParam(name = "fechaInicial", value = "Fecha inicial reporte", required = true) @RequestParam(value = "fechaInicial", required = true) Date fechaInicial,
            @ApiParam(name = "fechaFinal", value = "Fecha final reporte", required = true) @RequestParam(value = "fechaFinal", required = true) Date fechaFinal) {
        try {
            List<EstadoCuentaDTO> listaMovimientos = movimientoService.obtenerEstadoCuenta(clienteId, fechaInicial, fechaFinal);
            return new ResponseEntity<List<EstadoCuentaDTO>>(listaMovimientos, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
