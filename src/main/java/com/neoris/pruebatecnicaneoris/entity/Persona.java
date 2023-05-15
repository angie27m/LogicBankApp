package com.neoris.pruebatecnicaneoris.entity;

import javax.persistence.*;

@Entity
@Table(name = Persona.TABLE_NAME, schema = Persona.SCHEMA_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = Persona.SEQUENCE_NAME, sequenceName = Persona.SEQUENCE_NAME, allocationSize = 1)
public class Persona {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "persona";
    public static final String SCHEMA_NAME = "public";
    public static final String SEQUENCE_NAME = Persona.SCHEMA_NAME + ".seq_" + TABLE_NAME;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private Long id;

    private String nombre;

    private String genero;

    private Integer edad;

    private Long identificacion;

    private String direccion;

    private Long telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
}
