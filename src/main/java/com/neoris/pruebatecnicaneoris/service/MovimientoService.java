package com.neoris.pruebatecnicaneoris.service;

import com.neoris.pruebatecnicaneoris.dto.EstadoCuentaDTO;
import com.neoris.pruebatecnicaneoris.dto.MovimientoDTO;
import com.neoris.pruebatecnicaneoris.entity.Cuenta;
import com.neoris.pruebatecnicaneoris.entity.Movimiento;
import com.neoris.pruebatecnicaneoris.exception.BussinesLogicException;
import com.neoris.pruebatecnicaneoris.exception.ResourceNotFoundException;
import com.neoris.pruebatecnicaneoris.repository.CuentaRepository;
import com.neoris.pruebatecnicaneoris.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
 * Clase que permite tener el control lógico de los microservicios expuestos en relación a los movimientos
 */
@Service()
public class MovimientoService {

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    /**
     * Metodo que permite obtener los movimientos de una cuenta
     *
     * @param numeroCuenta Número de la cuenta del cliente
     */
    public List<MovimientoDTO> obtenerMovimientosCuenta(Long numeroCuenta) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findAllByNumeroCuenta(numeroCuenta);
        movimientos.stream().forEach(movimiento -> {
            MovimientoDTO nuevoMovimiento = new MovimientoDTO();
            nuevoMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
            nuevoMovimiento.setFecha(movimiento.getFecha());
            nuevoMovimiento.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
            nuevoMovimiento.setValor(movimiento.getValor());
            nuevoMovimiento.setTipo(movimiento.getCuenta().getTipo());
            movimientosDTO.add(nuevoMovimiento);
        });
        return movimientosDTO;
    }

    /**
     * Realiza un movimiento en una cuenta de banco
     *
     * @param movimiento Entidad que contiene información de un movimiento
     */
    public Object realizarMovimientoCuenta(MovimientoDTO movimiento) {
        Movimiento nuevoMovimiento = new Movimiento();
        if (movimiento != null) {
            if (movimiento.getValor() == null) {
                throw new BussinesLogicException("Valor no permitido");
            }

            // Verifica el tipo de movimiento (crédito o débito)
            String operacion = movimiento.getValor() >= 0 ? "credito" : "debito";
            nuevoMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
            nuevoMovimiento.setFecha(new Date());
            nuevoMovimiento.setValor(movimiento.getValor());
            Optional<Cuenta> optCuenta = cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta());
            nuevoMovimiento.setCuenta(optCuenta.get());
            // Valida el saldo en la cuenta en movimientos débito
            if (operacion.equals("debito")) {
                if (validarSaldoCuenta(optCuenta.get(), movimiento.getValor())) {
                    nuevoMovimiento.setSaldo(optCuenta.get().getSaldoInicial() - movimiento.getValor());
                } else {
                    return "Saldo no disponible";
                }
            } else {
                nuevoMovimiento.setSaldo(optCuenta.get().getSaldoInicial() + movimiento.getValor());
            }
            movimientoRepository.save(nuevoMovimiento);
        }
        return nuevoMovimiento;
    }

    /**
     * Método que valida si una cuenta tiene el saldo suficiente para un débito
     *
     * @param cuenta Entidad que contiene información de la cuenta
     * @param valor  Valor a retirar de la cuenta
     */
    private boolean validarSaldoCuenta(Cuenta cuenta, Double valor) {
        if (cuenta.getSaldoInicial() - valor <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Actualiza la información de un movimiento en una cuenta
     *
     * @param id         Identificador del movimiento
     * @param movimiento Entidad que contiene información de un movimiento
     */
    public Movimiento actualizarMovimientoCuenta(Long id, Movimiento movimiento) {
        Movimiento movimientoEncontrado = new Movimiento();
        Optional<Movimiento> optMovimiento = movimientoRepository.findById(id);

        if (optMovimiento.isEmpty()) {
            throw new ResourceNotFoundException("No existe el movimiento ingresado");
        } else {
            movimientoEncontrado = optMovimiento.get();
            if (movimiento.getTipoMovimiento() != null)
                movimientoEncontrado.setTipoMovimiento(movimiento.getTipoMovimiento());
            if (movimiento.getFecha() != null) movimientoEncontrado.setFecha(movimiento.getFecha());
            if (movimiento.getValor() != null) movimientoEncontrado.setValor(movimiento.getValor());
            if (movimiento.getCuenta() != null) movimientoEncontrado.setCuenta(movimiento.getCuenta());
            movimientoRepository.save(movimientoEncontrado);
        }
        return movimientoEncontrado;
    }

    /**
     * Elimina un movimiento de una cuenta
     *
     * @param id Identificador del movimiento
     */
    public boolean eliminarMovimientoCuenta(Long id) {
        boolean isRemoved;
        Movimiento movimientoEncontrado = new Movimiento();
        Optional<Movimiento> optionalMovimiento = movimientoRepository.findById(id);
        if (optionalMovimiento.isEmpty()) {
            isRemoved = false;
            throw new ResourceNotFoundException("No existe el movimiento ingresado");
        } else {
            movimientoEncontrado = optionalMovimiento.get();
            movimientoRepository.delete(movimientoEncontrado);
            isRemoved = true;
        }
        return isRemoved;
    }

    /**
     * Genera un reporte para un cliente en un período de tiempo especificado
     *
     * @param clienteId    Identificador del cliente
     * @param fechaInicial Fecha de inicio reporte movimientos
     * @param fechaFinal   Fecha final de reporte movimientos
     */
    public List<EstadoCuentaDTO> obtenerEstadoCuenta(Long clienteId, Date fechaInicial, Date fechaFinal) {

        List<EstadoCuentaDTO> reporteCuenta = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findAllByClienteYFechas(clienteId, fechaInicial, fechaFinal);
        movimientos.stream().forEach(movimiento -> {
            EstadoCuentaDTO nuevoMovimiento = new EstadoCuentaDTO();
            nuevoMovimiento.setFecha(movimiento.getFecha());
            nuevoMovimiento.setCliente(movimiento.getCuenta().getCliente().getNombre());
            nuevoMovimiento.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
            nuevoMovimiento.setTipo(movimiento.getCuenta().getTipo());
            nuevoMovimiento.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
            nuevoMovimiento.setEstado(movimiento.getCuenta().getEstado());
            nuevoMovimiento.setMovimiento(movimiento.getTipoMovimiento());
            nuevoMovimiento.setSaldoDisponible(movimiento.getSaldo());
            reporteCuenta.add(nuevoMovimiento);
        });
        return reporteCuenta;
    }
}
