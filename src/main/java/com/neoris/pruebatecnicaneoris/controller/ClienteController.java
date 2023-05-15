package com.neoris.pruebatecnicaneoris.controller;

import com.neoris.pruebatecnicaneoris.dto.ClienteDTO;
import com.neoris.pruebatecnicaneoris.entity.Cliente;
import com.neoris.pruebatecnicaneoris.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Clase que contiene los servicios correspondientes a la entidad Cliente
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @ApiOperation(value = "Metodo que permite obtener los clientes de un banco")
    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> obtenerClientes() {
        try {
            List<ClienteDTO> listaClientes = clienteService.obtenerClientes();
            return new ResponseEntity<List<ClienteDTO>>(listaClientes, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Guarda un cliente del banco")
    @PostMapping()
    public ResponseEntity<Object> guardarCliente(
            @RequestBody Cliente cliente) {
        try {
            clienteService.guardarCliente(cliente);
            return new ResponseEntity<Object>("Cliente "+ cliente.getNombre() + " agregado con éxito", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Actualiza la información de un cliente del banco")
    @PutMapping("{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable(value = "id") Long clienteId,
                                                     @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(clienteService.actualizarCliente(clienteId, cliente));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "Elimina un cliente de un banco")
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable(value = "id") Long clienteId) {
        try {
            boolean fueEliminado = clienteService.eliminarCliente(clienteId);

            if (!fueEliminado) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Cliente "+ clienteId + " eliminado con éxito", HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
