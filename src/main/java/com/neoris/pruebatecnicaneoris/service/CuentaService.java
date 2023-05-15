package com.neoris.pruebatecnicaneoris.service;

import com.neoris.pruebatecnicaneoris.dto.CuentaDTO;
import com.neoris.pruebatecnicaneoris.entity.Cuenta;
import com.neoris.pruebatecnicaneoris.exception.ResourceNotFoundException;
import com.neoris.pruebatecnicaneoris.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service()
public class CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    public List<CuentaDTO> obtenerCuentasCliente(Long clienteId) {
        List<CuentaDTO> cuentasDTO = new ArrayList<>();
        List<Cuenta> cuentas = cuentaRepository.findAllByClienteId(clienteId);
        cuentas.stream().forEach(cuenta -> {
            CuentaDTO nuevaCuenta = new CuentaDTO();
            nuevaCuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
            nuevaCuenta.setTipo(cuenta.getTipo());
            nuevaCuenta.setSaldoInicial(cuenta.getSaldoInicial());
            nuevaCuenta.setEstado(cuenta.getEstado());
            nuevaCuenta.setCliente(cuenta.getCliente());
            cuentasDTO.add(nuevaCuenta);
        });
        return cuentasDTO;
    }

    public Object crearCuentaCliente(Cuenta cuenta) {
        Cuenta cuentaNueva = new Cuenta();
        if (cuenta != null) {
            cuentaNueva = cuentaRepository.save(cuenta);
        }
        return cuentaNueva;
    }

    public Cuenta actualizarCuentaCliente(Long numeroCuenta, Cuenta cuenta) {
        Cuenta cuentaEncontrada = new Cuenta();
        Optional<Cuenta> optCuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);

        if (optCuenta.isEmpty()) {
            System.out.println("No existe esta cuenta de cliente");
            //throw new ResourceNotFoundException("No existe cliente con id: " + id);
        } else {
            cuentaEncontrada = optCuenta.get();
            if (cuenta.getNumeroCuenta() != null) cuentaEncontrada.setNumeroCuenta(cuenta.getNumeroCuenta());
            if (cuenta.getTipo() != null) cuentaEncontrada.setTipo(cuenta.getTipo());
            if (cuenta.getSaldoInicial() != null) cuentaEncontrada.setSaldoInicial(cuenta.getSaldoInicial());
            if (cuenta.getEstado() != null) cuentaEncontrada.setEstado(cuenta.getEstado());
            if (cuenta.getCliente() != null) cuentaEncontrada.setCliente(cuenta.getCliente());

            cuentaRepository.save(cuentaEncontrada);
        }
        return cuentaEncontrada;
    }

    public boolean eliminarCuentaCliente(Long numeroCuenta) {
        boolean isRemoved;
        Cuenta cuentaEncontrada = new Cuenta();
        Optional<Cuenta> optCuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (optCuenta.isEmpty()) {
            isRemoved = false;
            throw new ResourceNotFoundException("No existe cuenta con número de cuenta: " + numeroCuenta);
        } else {
            cuentaEncontrada = optCuenta.get();
            cuentaRepository.delete(cuentaEncontrada);
            isRemoved = true;
        }
        return isRemoved;
    }
}
