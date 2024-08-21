package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La clase ClienteEntrenamiento representa la entidad que vincula a un cliente con su
 * programa de entrenamiento, incluyendo detalles sobre los ejercicios, recursos multimedia,
 * y la planificación de la frecuencia del entrenamiento.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA que será
 * mapeada a una tabla en la base de datos.</p>
 *
 * <p>La clase utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter y un constructor sin argumentos, respectivamente.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>clienteentrenamiento</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="clienteentrenamiento")
public class ClienteEntrenamiento {

    /**
     * Identificador único de la entidad ClienteEntrenamiento.
     * Es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Detalles específicos sobre los ejercicios que forman parte del entrenamiento del cliente.
     * Este campo puede incluir una descripción detallada de cada ejercicio.
     */
    @Column(name = "detalles_ejercicios")
    private String detallesEjercicios;

    /**
     * Recursos multimedia asociados con el programa de entrenamiento, tales como videos o imágenes
     * que sirven de guía para el cliente durante su rutina de ejercicios.
     */
    @Lob
    private byte[] recursosMultimedia;

    /**
     * Planificación de la frecuencia del entrenamiento, que puede incluir información sobre
     * la periodicidad con la que se deben realizar los ejercicios.
     */
    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia;

    /**
     * Cliente que es entrenado en este programa de entrenamiento. Representa una relación muchos a uno
     * con la entidad <code>Cliente</code>.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idclienteentrenado", foreignKey = @ForeignKey(name = "Fk_cliente_entrenamiento_cliente"))
    private Cliente clienteEntrenado;

    /**
     * Entrenador que supervisa y guía al cliente en su programa de entrenamiento.
     * Representa una relación muchos a uno con la entidad <code>Cliente</code>.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "identrenadorcliente", foreignKey = @ForeignKey(name = "Fk_cliente_entrenamiento_entrenador"))
    private Cliente entrenadorCliente;
}
