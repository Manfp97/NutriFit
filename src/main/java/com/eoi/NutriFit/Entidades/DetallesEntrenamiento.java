package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Set;

/**
 * La clase DetallesEntrenamiento representa la entidad que almacena los detalles de un entrenamiento específico,
 * incluyendo la cantidad de repeticiones, los períodos de descanso, y la descripción de los ejercicios.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, indicando que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter, y un constructor sin argumentos, respectivamente.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>Detalles_entrenamientos</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Detalles_entrenamientos")
public class DetallesEntrenamiento {

    /**
     * Identificador único de la entidad DetallesEntrenamiento.
     * Este campo es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Número de repeticiones realizadas en el entrenamiento. Este campo se utiliza para registrar
     * el volumen de repeticiones de cada ejercicio.
     */
    @Column(name = "volumen_repeticiones")
    private Integer volumen_repeticiones;

    /**
     * Período de descanso entre series o ejercicios, almacenado como un objeto <code>DecimalFormat</code>.
     * Se utiliza para formatear el tiempo de descanso en un formato específico.
     */
    @Column(name = "descansos")
    private DecimalFormat descansos;

    /**
     * Descripción detallada de los ejercicios incluidos en el entrenamiento. Este campo puede incluir
     * información sobre la técnica, los objetivos, y cualquier otra información relevante de los ejercicios.
     */
    @Column(name = "detalles_ejercicios")
    private String detalles_ejercicios;

    /**
     * Relación muchos a uno con la entidad <code>Entrenamiento</code>. Representa el entrenamiento
     * al que pertenecen estos detalles.
     */
    @ManyToOne
    private Entrenamiento identrenamiento;
}
