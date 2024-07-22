package com.eoi.NutriFit.Entidades;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Set;

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

    @Column(name = "nombre")
    private String nombre;


    @Column(name = "categoria")
    private String categoria;

    @Lob
    private byte [] recursos_multimedia;

    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    @Column(name = "objetivos")
    private String objetivos;

    @Column(name = "dificultad")
    private String dificultad;

    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "identrenamiento")
    private Set<DetallesEntrenamiento> DetallesEntrenamiento;

    @OneToMany
    private Set <ProgresionesEntrenamiento> entrenamientos_identrenamiento;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEntrenamiento")
    private Set<ProgresionesEntrenamiento> ProgresionesEntrenamiento;



}