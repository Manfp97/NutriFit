package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Detalles_entrenamientos")
public class DetallesEntrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "volumen_repeticiones")
    private Integer volumen_repeticiones;

    @Column(name = "descansos")
    private DecimalFormat descansos;

    @Column(name = "detalles_ejercicios")
    private String detalles_ejercicios;



    @OneToMany
    private Entrenamiento identrenamiento;


}