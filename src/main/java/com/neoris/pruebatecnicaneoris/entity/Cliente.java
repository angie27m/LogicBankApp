package com.neoris.pruebatecnicaneoris.entity;

import javax.persistence.*;

@Entity
@Table(name = Cliente.TABLE_NAME, schema = Cliente.SCHEMA_NAME)
@SequenceGenerator(name = Cliente.SEQUENCE_NAME, sequenceName = Cliente.SEQUENCE_NAME, allocationSize = 1)
@PrimaryKeyJoinColumn(name = "cliente_id", referencedColumnName = "id")
public class Cliente extends Persona {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "cliente";
    public static final String SCHEMA_NAME = "public";
    public static final String SEQUENCE_NAME = Cliente.SCHEMA_NAME + ".seq_" + TABLE_NAME;

    private String contrasenia;

    private Boolean estado;

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
