package com.neoris.pruebatecnicaneoris.entity;

import javax.persistence.*;

/*
 * Clase que representa la entidad Cuenta
 */
@Entity
@Table(name = Cuenta.TABLE_NAME, schema = Cuenta.SCHEMA_NAME)
@SequenceGenerator(name = Cuenta.SEQUENCE_NAME, sequenceName = Cuenta.SEQUENCE_NAME, allocationSize = 1)
public class    Cuenta {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "cuenta";
    public static final String SCHEMA_NAME = "public";
    public static final String SEQUENCE_NAME = Cliente.SCHEMA_NAME + ".seq_" + TABLE_NAME;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private Long id;

    @Column(name = "numero_cuenta")
    private Long numeroCuenta;

    private String tipo;

    @Column(name = "saldo_inicial")
    private Double saldoInicial;

    private Boolean estado;

    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
