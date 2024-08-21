package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * La clase {@code Carrito} representa un carrito de compras en la aplicación NutriFit.
 * Esta entidad está mapeada a la tabla "carrito" en la base de datos.
 *
 * <p>La clase {@code Carrito} está relacionada bidireccionalmente con las entidades {@code Cliente} y {@code Producto}.
 * La relación con {@code Cliente} es de uno a uno, mientras que la relación con {@code Producto} es de muchos a muchos.</p>
 *
 * <p>Se utilizan anotaciones de JPA para el mapeo de la entidad a la base de datos, y la biblioteca Lombok
 * para la generación automática de getters, setters, y un constructor sin argumentos.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @version 1.0
 * @since 2024
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "carrito")
public class Carrito {

    /**
     * Identificador único del carrito.
     * Este campo está mapeado a la columna "id" en la tabla "carrito".
     * Se genera automáticamente mediante la estrategia {@code GenerationType.AUTO}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Precio total de los productos en el carrito.
     * Este campo está mapeado a la columna "preciototal" en la tabla "carrito".
     */
    @Column(name = "preciototal")
    private int preciototal;

    /**
     * Relación uno a uno con la entidad {@code Cliente}.
     * Este campo representa el cliente al que pertenece el carrito.
     * Se utiliza la anotación {@code JoinColumn} para especificar la columna de unión "id_cliente".
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**
     * Relación muchos a muchos con la entidad {@code Producto}.
     * Este campo representa los productos que están en el carrito.
     * La relación se mapea a la tabla intermedia "carrito_producto", con las columnas de unión "idcarrito" e "idproducto".
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
