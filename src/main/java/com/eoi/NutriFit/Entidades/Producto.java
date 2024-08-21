package com.eoi.NutriFit.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * La clase Producto representa la entidad que almacena información sobre un producto disponible en el sistema,
 * incluyendo detalles como el código, nombre, descripción, categoría, precio y stock.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, indicando que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, <code>@NoArgsConstructor</code>,
 * y <code>@AllArgsConstructor</code> para generar automáticamente los métodos getter, setter, un constructor sin
 * argumentos, y un constructor con todos los argumentos, respectivamente.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>productos</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="productos")
public class Producto {

    /**
     * Identificador único del producto.
     * Este campo es autogenerado con la estrategia AUTO y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Código único asignado al producto para su identificación.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * Nombre del producto.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Descripción detallada del producto.
     * Este campo puede contener una descripción extensa de hasta 2000 caracteres.
     */
    @Column(name = "descripcion", length = 2000)
    private String descripcion;

    /**
     * Categoría a la que pertenece el producto, como alimentos, accesorios, etc.
     */
    @Column(name = "categoria")
    private String categoria;

    /**
     * Precio del producto en la moneda especificada.
     */
    @Column(name = "precio")
    private Double precio;

    /**
     * Cantidad de producto disponible en stock.
     */
    @Column(name = "stock")
    private Double stock;

    /**
     * Conjunto de carritos de compra que contienen este producto.
     * La relación muchos a muchos está mapeada mediante la tabla intermedia <code>producto_carrito</code>,
     * con columnas de clave foránea <code>idproducto</code> y <code>idcarrito</code>.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "producto_carrito",
            joinColumns = @JoinColumn(name = "idproducto", foreignKey = @ForeignKey(name = "Fk_producto_carrito_producto")),
            inverseJoinColumns = @JoinColumn(name = "idcarrito", foreignKey = @ForeignKey(name = "Fk_producto_carrito_carrito"))
    )
    private Set<Carrito> carritos = new HashSet<>();

    /**
     * Proveedor que suministra este producto.
     * La relación muchos a uno está mapeada mediante la columna <code>proveedor_id</code> en la entidad <code>Proveedores</code>.
     * La anotación <code>@JsonBackReference</code> se usa para evitar la serialización infinita en la conversión a JSON.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id", foreignKey = @ForeignKey(name = "Fk_proveedor_producto"))
    @JsonBackReference
    private Proveedores proveedor;

    /**
     * Constructor para crear una instancia de Producto con los parámetros especificados.
     *
     * @param codigo Código único asignado al producto.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción detallada del producto.
     * @param categoria Categoría a la que pertenece el producto.
     * @param precio Precio del producto.
     * @param stock Cantidad de producto disponible en stock.
     * @param proveedor Proveedor que suministra el producto.
     */
    public Producto(String codigo, String nombre, String descripcion, String categoria,
                    Double precio, Double stock, Proveedores proveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }
}
