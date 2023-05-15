package com.neoris.pruebatecnicaneoris.service;

import com.neoris.pruebatecnicaneoris.dto.ClienteDTO;
import com.neoris.pruebatecnicaneoris.entity.Cliente;
import com.neoris.pruebatecnicaneoris.exception.ResourceNotFoundException;
import com.neoris.pruebatecnicaneoris.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Clase que permite tener el control lógico de los microservicios expuestos en relación a los clientes
 */
@Service()
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    /**
     * Metodo que permite obtener los clientes de un banco
     */
    public List<ClienteDTO> obtenerClientes() {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.stream().forEach(cliente -> {
            ClienteDTO nuevoCliente = new ClienteDTO();
            nuevoCliente.setClienteId(cliente.getId());
            nuevoCliente.setNombre(cliente.getNombre());
            nuevoCliente.setContrasenia(cliente.getContrasenia());
            nuevoCliente.setDireccion(cliente.getDireccion());
            nuevoCliente.setTelefono(cliente.getTelefono());
            nuevoCliente.setEstado(cliente.getEstado());
            clientesDTO.add(nuevoCliente);
        });
        return clientesDTO;
    }

    /**
     * Guarda un cliente del banco con su respectiva información
     *
     * @param cliente Entidad que contiene información del cliente
     */
    public Cliente guardarCliente(Cliente cliente) {
        Cliente clienteNuevo = new Cliente();
        if (cliente != null) {
            clienteNuevo = clienteRepository.save(cliente);
        } else {

        }
        return clienteNuevo;
    }

    /**
     * Actualiza la información de un cliente del banco
     *
     * @param clienteId Identificador del cliente
     * @param cliente   Entidad que contiene información del cliente
     */
    public Cliente actualizarCliente(Long clienteId, Cliente cliente) {
        Cliente clienteEncontrado = new Cliente();
        Optional<Cliente> optCliente = clienteRepository.findByClienteId(clienteId);

        if (optCliente.isEmpty()) {
            throw new ResourceNotFoundException("No existe cliente con id: " + clienteId);
        } else {
            clienteEncontrado = optCliente.get();
            if (cliente.getNombre() != null) clienteEncontrado.setNombre(cliente.getNombre());
            if (cliente.getGenero() != null) clienteEncontrado.setGenero(cliente.getGenero());
            if (cliente.getEdad() != null) clienteEncontrado.setEdad(cliente.getEdad());
            if (cliente.getIdentificacion() != null) clienteEncontrado.setIdentificacion(cliente.getIdentificacion());
            if (cliente.getDireccion() != null) clienteEncontrado.setDireccion(cliente.getDireccion());
            if (cliente.getTelefono() != null) clienteEncontrado.setTelefono(cliente.getTelefono());
            if (cliente.getContrasenia() != null) clienteEncontrado.setContrasenia(cliente.getContrasenia());
            if (cliente.getEstado() != null) clienteEncontrado.setEstado(cliente.getEstado());

            clienteRepository.save(clienteEncontrado);
        }
        return clienteEncontrado;
    }

    /**
     * Elimina un cliente de un banco
     *
     * @param clienteId Identificador del cliente
     */
    public boolean eliminarCliente(Long clienteId) {
        Cliente clienteEncontrado = new Cliente();
        boolean isRemoved;
        Optional<Cliente> optCliente = clienteRepository.findByClienteId(clienteId);
        if (optCliente.isEmpty()) {
            isRemoved = false;
            throw new ResourceNotFoundException("No existe cliente con id: " + clienteId);
        } else {
            clienteEncontrado = optCliente.get();
            clienteRepository.delete(clienteEncontrado);
            isRemoved = true;
        }
        return isRemoved;
    }
}
