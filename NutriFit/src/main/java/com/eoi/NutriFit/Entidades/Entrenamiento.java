package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Entrenamiento")
public class Entrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "categoria")
    private String categoria;

    @Lob
    private byte [] recursos_multimedia;

    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    @Column(name = "objetivos")
    private String objetivos;

    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia;


    /*
    @ManyToOne
    @Column(name = "idcontacto_entrenamiento")
    private ClienteEntrenamiento idcontactoentrenamiento;

    @OneToMany(mappedBy = "entrenamientos_identrenamiento")
    private ProgresionesEntrenamiento entrenamientos_identrenamiento;

    @ManyToOne
    @Column(name = "id_detalles")
    private DetallesEntrenamiento id_detalles;

    */


}
