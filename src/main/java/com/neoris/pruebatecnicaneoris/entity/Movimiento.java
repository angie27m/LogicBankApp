package com.neoris.pruebatecnicaneoris.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Movimiento.TABLE_NAME, schema = Movimiento.SCHEMA_NAME)
@SequenceGenerator(name = Movimiento.SEQUENCE_NAME, sequenceName = Movimiento.SEQUENCE_NAME, allocationSize = 1)
public class Movimiento {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "movimiento";
    public static final String SCHEMA_NAME = "public";
    public static final String SEQUENCE_NAME = Cliente.SCHEMA_NAME + ".seq_" + TABLE_NAME;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private Long id;

    private Date fecha;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    private Double valor;

    private Double saldo;

    @ManyToOne()
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
