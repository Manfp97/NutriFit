package org.cplcursos.java.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.sql.Blob;

public class Dieta {
    @Id
    @Column(name = "id", nullable = false)
    private Integer iddieta;

    @Column (name = "objetivos")
    private String objetivos;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "recursos_multimedia")
    private Blob recursos_multimedia;

    @Column (name = "planificacion_frecuencia")
    private String planificacion_frecuencia;


}
