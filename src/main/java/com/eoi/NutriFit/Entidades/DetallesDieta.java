package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * La clase DetallesDieta representa la entidad que almacena los detalles específicos de una dieta,
 * incluyendo la cantidad de alimentos y una descripción detallada de los mismos.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA que se mapeará
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter, y un constructor sin argumentos.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>detalles_dietas</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="detalles_dietas")
public class DetallesDieta {

    /**
     * Identificador único de la entidad DetallesDieta.
     * Este campo es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Cantidad del alimento especificado en la dieta. Este campo utiliza <code>BigDecimal</code>
     * para representar con precisión valores numéricos, especialmente para cantidades fraccionarias.
     */
    @Column(name = "cantidad")
    private BigDecimal cantidad;

    /**
     * Detalles específicos de los alimentos incluidos en la dieta, que pueden describir
     * las características, preparación o cualquier otra información relevante del alimento.
     */
    @Column(name = "detalles_alimentos")
    private String detalles_alimentos;

    /**
     * Relación muchos a uno con la entidad <code>Dieta</code>, que representa la dieta
     * a la que pertenecen estos detalles.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddieta", foreignKey = @ForeignKey(name = "Fk_detalles_dieta"))
    private Dieta dieta;
}
