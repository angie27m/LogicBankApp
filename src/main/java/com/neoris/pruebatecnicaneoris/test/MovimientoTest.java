package com.neoris.pruebatecnicaneoris.test;

import com.neoris.pruebatecnicaneoris.dto.MovimientoDTO;
import com.neoris.pruebatecnicaneoris.entity.Cliente;
import com.neoris.pruebatecnicaneoris.entity.Cuenta;
import com.neoris.pruebatecnicaneoris.entity.Movimiento;
import com.neoris.pruebatecnicaneoris.repository.MovimientoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MovimientoTest {

    @Autowired
    MovimientoRepository movimientoRepository;

    @Test
    public void testValidarSaldoCuenta() {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Marianela Montalvo");
        cliente.setGenero("Femenino");
        cliente.setEdad(34);
        cliente.setIdentificacion(1073673L);
        cliente.setDireccion("Amazonas y NNUU");
        cliente.setIdentificacion(97548965L);
        cliente.setContrasenia("1234");
        cliente.setEstado(true);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(478758L);
        cuenta.setTipo("Ahorro");
        cuenta.setSaldoInicial(2000.0);
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);

        Double valor1 = 575.0;
        Double valor2 = 2345.0;

        Assertions.assertEquals(false, cuenta.getSaldoInicial() - valor1 <= 0);
        Assertions.assertEquals(true, cuenta.getSaldoInicial() - valor2 <= 0);
    }

    @Test
    public void testObtenerMovimientosCuenta() {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findAllByNumeroCuenta(123L);
        movimientos.stream().forEach(movimiento -> {
            MovimientoDTO nuevoMovimiento = new MovimientoDTO();
            nuevoMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
            nuevoMovimiento.setFecha(movimiento.getFecha());
            nuevoMovimiento.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
            nuevoMovimiento.setValor(movimiento.getValor());
            nuevoMovimiento.setTipo(movimiento.getCuenta().getTipo());
            movimientosDTO.add(nuevoMovimiento);
        });

        List<MovimientoDTO> listadoVacio = new ArrayList<>();
        Assertions.assertEquals(listadoVacio, movimientosDTO);
    }

}
