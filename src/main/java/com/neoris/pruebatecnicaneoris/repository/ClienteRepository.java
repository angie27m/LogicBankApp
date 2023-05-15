package com.neoris.pruebatecnicaneoris.repository;

import com.neoris.pruebatecnicaneoris.entity.Cliente;
import com.neoris.pruebatecnicaneoris.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Clase que permite la comunicación con la tabla cliente en base de datos
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = "select * from public.persona p " +
            "join public.cliente c on c.cliente_id = p.id " +
            "where cliente_id = ?1", nativeQuery = true)
    Optional<Cliente> findByClienteId(Long clienteId);


}
