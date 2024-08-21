package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un carrito de compras en el sistema.
 * Un carrito está asociado a un cliente y puede contener múltiples productos.
 *
 * La entidad Carrito establece una relación bidireccional uno a uno con {@link Cliente}
 * y una relación bidireccional muchos a muchos con {@link Producto}.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "carrito")
public class Carrito {

    /**
     * Identificador único del carrito.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Precio total de los productos en el carrito.
     */
    @Column(name = "preciototal")
    private int preciototal;

    /**
     * El cliente al que pertenece este carrito.
     * Establece una relación uno a uno con la entidad {@link Cliente}.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**
     * Conjunto de productos contenidos en este carrito.
     * Establece una relación muchos a muchos con la entidad {@link Producto}.
     * La relación se mapea en la tabla intermedia "carrito_producto".
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "carrito_producto",
            joinColumns = @JoinColumn(name = "idcarrito", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idproducto", referencedColumnName = "id")
    )
    private Set<Producto> productos = new HashSet<>();

    // Constructores, getters, setters, y otros métodos según sea necesario
}
