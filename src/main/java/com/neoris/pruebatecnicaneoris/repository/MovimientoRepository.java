package com.neoris.pruebatecnicaneoris.repository;

import com.neoris.pruebatecnicaneoris.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    @Query(value = "select * from public.movimiento m " +
            "join public.cuenta c on c.id = m.cuenta_id " +
            "where c.numero_cuenta =?1", nativeQuery = true)
    List<Movimiento> findAllByNumeroCuenta(Long numeroCuenta);

    Optional<Movimiento> findById(Long id);

    @Query(value = "select * from public.movimiento m " +
            "join public.cuenta c on c.id = m.cuenta_id " +
            "join public.cliente cl on cl.cliente_id = c.cliente_id " +
            "where cl.cliente_id = ?1 and m.fecha >= ?2 and m.fecha <= ?3", nativeQuery = true)
    List<Movimiento> findAllByClienteYFechas(Long clienteId, Date fechaInicial, Date fechaFinal);
}
