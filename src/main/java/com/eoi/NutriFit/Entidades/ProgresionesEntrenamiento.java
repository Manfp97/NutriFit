package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa las progresiones de entrenamiento de un usuario.
 * <p>
 * La entidad {@code ProgresionesEntrenamiento} almacena información sobre el progreso de un entrenamiento específico,
 * incluyendo la fecha, repeticiones, series, peso y el ejercicio realizado.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Progresiones_entrenamientos")
public class ProgresionesEntrenamiento {

    /**
     * Identificador único de la progresión de entrenamiento.
     * <p>
     * Es una columna autoincrementable en la base de datos.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Fecha en la que se registró la progresión de entrenamiento.
     * <p>
     * Este campo almacena la fecha en que se realizó el entrenamiento.
     * </p>
     */
    @Column(name = "fecha")
    private LocalDate fecha;

    /**
     * Número de repeticiones realizadas en el ejercicio.
     * <p>
     * Este campo almacena la cantidad de repeticiones hechas en la sesión de entrenamiento.
     * </p>
     */
    @Column(name = "repeticiones")
    private int repeticiones;

    /**
     * Número de series realizadas en el ejercicio.
     * <p>
     * Este campo almacena la cantidad de series completas realizadas durante la sesión de entrenamiento.
     * </p>
     */
    @Column(name = "series")
    private int series;

    /**
     * Peso utilizado en el ejercicio.
     * <p>
     * Este campo almacena el peso (en unidades del sistema métrico) que se utilizó durante el entrenamiento.
     * </p>
     */
    @Column(name = "peso")
    private int peso;

    /**
     * Nombre del ejercicio realizado.
     * <p>
     * Este campo almacena el nombre del ejercicio específico que se realizó durante la sesión.
     * </p>
     */
    @Column(name = "ejercicio")
    private String ejercicio;

    /**
     * Entrenamiento al que pertenece esta progresión.
     * <p>
     * Establece una relación muchos a uno con la entidad {@code Entrenamiento}.
     * Este campo define el entrenamiento al que se asocia esta progresión de entrenamiento.
     * </p>
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEntrenamiento")
    private Entrenamiento entrenamiento;
}
