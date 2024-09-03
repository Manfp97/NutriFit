package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Set;

/**
 * La clase Entrenamiento representa la entidad que almacena información sobre un plan de entrenamiento,
 * incluyendo detalles como el nombre, categoría, descripción, y recursos multimedia asociados.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, indicando que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, <code>@NoArgsConstructor</code>
 * y <code>@AllArgsConstructor</code> para generar automáticamente los métodos getter, setter, un constructor sin
 * argumentos, y un constructor con todos los argumentos, respectivamente.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>Entrenamiento</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Entrenamiento")
public class Entrenamiento {

    /**
     * Identificador único de la entidad Entrenamiento.
     * Este campo es autogenerado con la estrategia AUTO y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre del entrenamiento. Este campo almacena el nombre asignado al plan de entrenamiento.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Categoría del entrenamiento, como puede ser fuerza, resistencia, cardio, etc.
     */
    @Column(name = "categoria")
    private String categoria;

    /**
     * Descripción detallada del entrenamiento, que puede incluir información sobre el plan de ejercicios, técnicas, etc.
     */
    @Column(name ="descripcion")
    private String descripcion;

    /**
     * Recursos multimedia asociados con el entrenamiento, tales como imágenes, videos o documentos.
     * Este campo se almacena como un arreglo de bytes (BLOB).
     */
    @Lob
    @Column(name = "recursos_multimedia")
    private byte[] recursos_multimedia;

    /**
     * Grupo muscular objetivo del entrenamiento, como pecho, espalda, piernas, etc.
     */
    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    /**
     * Objetivos del entrenamiento, como pérdida de peso, aumento de masa muscular, mejora de la resistencia, etc.
     */
    @Column(name = "objetivos")
    private String objetivos;

    /**
     * Nivel de dificultad del entrenamiento, que puede indicar si el entrenamiento es para principiantes, intermedios o avanzados.
     */
    @Column(name = "dificultad")
    private String dificultad;

    /**
     * Planificación de la frecuencia del entrenamiento, que indica con qué frecuencia se debe realizar el plan (diariamente, semanalmente, etc.).
     */
    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia;

    /**
     * Conjunto de detalles específicos asociados con este entrenamiento.
     * Esta relación uno a muchos está mapeada por el campo <code>identrenamiento</code> en la entidad <code>DetallesEntrenamiento</code>.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "identrenamiento")
    private Set<DetallesEntrenamiento> DetallesEntrenamiento;

    /**
     * Conjunto de registros de progresión asociados con este entrenamiento.
     * Esta relación uno a muchos está mapeada por el campo <code>entrenamientos_identrenamiento</code> en la entidad <code>ProgresionesEntrenamiento</code>.
     */
    @OneToMany
    private Set<ProgresionesEntrenamiento> entrenamientos_identrenamiento;

    /**
     * Conjunto de registros de progresión asociados con este entrenamiento.
     * Esta relación uno a muchos está mapeada por el campo <code>idEntrenamiento</code> en la entidad <code>ProgresionesEntrenamiento</code>.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEntrenamiento")
    private Set<ProgresionesEntrenamiento> ProgresionesEntrenamiento;

    /**
     * Constructor para crear una instancia de Entrenamiento con los parámetros especificados.
     *
     * @param nombre Nombre del entrenamiento.
     * @param categoria Categoría del entrenamiento.
     * @param recursos_multimedia Recursos multimedia asociados con el entrenamiento.
     * @param grupoMuscular Grupo muscular objetivo del entrenamiento.
     * @param objetivos Objetivos del entrenamiento.
     * @param dificultad Nivel de dificultad del entrenamiento.
     * @param planificacionFrecuencia Planificación de la frecuencia del entrenamiento.
     */
    public Entrenamiento(String nombre, String categoria, byte[] recursos_multimedia, String grupoMuscular,
                         String objetivos, String dificultad, String planificacionFrecuencia) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.recursos_multimedia = recursos_multimedia;
        this.grupoMuscular = grupoMuscular;
        this.objetivos = objetivos;
        this.dificultad = dificultad;
        this.planificacionFrecuencia = planificacionFrecuencia;
    }
}
