package com.eoi.NutriFit.Entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * La clase <code>Proveedores</code> representa la entidad que almacena información sobre los proveedores
 * de productos en el sistema. Incluye detalles sobre el nombre del proveedor, el contacto y la dirección.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, indicando que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@NoArgsConstructor</code>
 * para generar automáticamente los métodos getter, setter, y un constructor sin argumentos.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>proveedores</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "proveedores")
public class Proveedores {

    /**
     * Identificador único del proveedor.
     * Este campo es autogenerado con la estrategia AUTO.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    /**
     * Nombre del proveedor.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Información de contacto del proveedor.
     */
    @Column(name = "contacto")
    private String contacto;

    /**
     * Dirección del proveedor.
     */
    @Column(name = "direccion")
    private String direccion;

    /**
     * Conjunto de productos asociados a este proveedor.
     * La relación uno a muchos está mapeada mediante la columna <code>proveedor</code> en la entidad <code>Producto</code>.
     * La anotación <code>@JsonManagedReference</code> se utiliza para gestionar la referencia circular en la serialización JSON.
     */
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Producto> productos = new HashSet<>();

    /**
     * Constructor parametrizado para crear una instancia de <code>Proveedores</code> con nombre, contacto y dirección.
     *
     * @param nombre    El nombre del proveedor.
     * @param contacto  La información de contacto del proveedor.
     * @param direccion La dirección del proveedor.
     */
    public Proveedores(String nombre, String contacto, String direccion) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.direccion = direccion;
    }
}
