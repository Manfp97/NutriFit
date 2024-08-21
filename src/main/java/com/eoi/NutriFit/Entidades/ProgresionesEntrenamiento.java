package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * La clase ProgresionesEntrenamiento representa la entidad que almacena información sobre el progreso
 * en el entrenamiento. Incluye detalles como la fecha del progreso, el número de repeticiones, series,
 * y peso utilizado durante el entrenamiento.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, indicando que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter, y un constructor sin argumentos.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>Progresiones_entrenamientos</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Progresiones_entrenamientos")
public class ProgresionesEntrenamiento {

    /**
     * Identificador único de la progresión del entrenamiento.
     * Este campo es autogenerado con la estrategia AUTO y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Fecha en la que se registró la progresión del entrenamiento.
     */
    @Column(name = "fecha")
    private LocalDate fecha;

    /**
     * Número de repeticiones realizadas durante el entrenamiento en la fecha especificada.
     */
    @Column(name = "repeticiones")
    private int repeticiones;

    /**
     * Número de series realizadas durante el entrenamiento en la fecha especificada.
     */
    @Column(name = "series")
    private int series;

    /**
     * Peso utilizado durante el entrenamiento en la fecha especificada, medido en unidades relevantes.
     */
    @Column(name = "peso")
    private int peso;

    /**
     * Entrenamiento asociado con esta progresión.
     * La relación muchos a uno está mapeada mediante la columna <code>idEntrenamiento</code> en la entidad <code>Entrenamiento</code>.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEntrenamiento")
    private Entrenamiento entrenamiento;
}
