package com.neoris.pruebatecnicaneoris.repository;

import com.neoris.pruebatecnicaneoris.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

    List<Cuenta> findAllByClienteId(Long clienteId);

    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);
}
