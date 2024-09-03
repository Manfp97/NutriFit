package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase ProgresionDieta representa la entidad que almacena información sobre el progreso de una dieta específica.
 * Incluye detalles sobre el peso inicial y el peso objetivo del usuario en el contexto de una dieta.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, indicando que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter, y un constructor sin argumentos.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>progresion_dietas</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="progresion_dietas")
public class ProgresionDieta {

    /**
     * Identificador único de la progresión de la dieta.
     * Este campo es autogenerado con la estrategia AUTO.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    /**
     * Peso inicial del usuario al comenzar la dieta.
     */
    @Column(name = "peso_incial")
    private Integer peso_incial;

    /**
     * Peso objetivo del usuario al finalizar la dieta.
     */
    @Column(name = "peso_objetivo")
    private Integer peso_objetivo;

    /**
     * Dieta asociada con esta progresión.
     * La relación muchos a uno está mapeada mediante la columna <code>dieta_id</code> en la entidad <code>Dieta</code>.
     */
    @ManyToOne
    @JoinColumn(name = "dieta_id")
    private Dieta dieta;
}
