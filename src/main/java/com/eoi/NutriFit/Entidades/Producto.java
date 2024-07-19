package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.eoi.NutriFit.Entidades.Producto;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Double stock;

    @ManyToMany
    @JoinTable(
            name = "producto_carrito",
            joinColumns = @JoinColumn(name = "idproducto", foreignKey = @ForeignKey(name = "Fk_producto_carrito_producto")),
            inverseJoinColumns = @JoinColumn(name = "idcarrito", foreignKey = @ForeignKey(name = "Fk_producto_carrito_carrito"))
    )
    private Set<Carrito> carritos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "proveedor_id", foreignKey = @ForeignKey(name = "Fk_proveedor_producto"))
    private Proveedores proveedor;
}

