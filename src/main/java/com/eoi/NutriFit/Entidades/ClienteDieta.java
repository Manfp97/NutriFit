package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * La clase ClienteDieta representa la entidad que vincula a un cliente con una dieta específica,
 * incluyendo detalles adicionales como el horario de la dieta y las relaciones con otras entidades
 * como Cliente y Dieta.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA que será
 * mapeada a una tabla en la base de datos.</p>
 *
 * <p>La clase utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter y un constructor sin argumentos, respectivamente.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>contacto_dietas</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="contacto_dietas")
public class ClienteDieta {

    /**
     * Identificador único de la entidad ClienteDieta.
     * Es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Detalles adicionales sobre la dieta asociada al cliente.
     * Este campo puede incluir información personalizada sobre la dieta.
     */
    @Column(name = "detalles_dietas")
    private String detalles_dietas;

    /**
     * Horario en el que se sigue la dieta o se tiene una cita relacionada con la dieta.
     * Es un campo que almacena información temporal.
     */
    @Column(name = "horario")
    private LocalDateTime horario;

    /**
     * Conjunto de dietas asociadas al cliente. Representa la relación uno a muchos
     * con la entidad <code>Dieta</code>.
     */
    @OneToMany
    private Set<Dieta> dieta;

    /**
     * Cliente asociado con esta dieta. Representa una relación muchos a uno
     * con la entidad <code>Cliente</code>.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    /**
     * Dietista que ha creado o está supervisando esta dieta. Representa una relación muchos a uno
     * con la entidad <code>Cliente</code>, aunque en este caso actúa como dietista.
     */
    @ManyToOne
    @JoinColumn(name = "id_dietista", referencedColumnName = "id")
    private Cliente dietista;
}
