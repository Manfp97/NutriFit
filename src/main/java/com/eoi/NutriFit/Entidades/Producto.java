package com.eoi.NutriFit.Entidades;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion",length = 2000)
    private String descripcion;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Double stock;





    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "producto_carrito",
            joinColumns = @JoinColumn(name = "idproducto", foreignKey = @ForeignKey(name = "Fk_producto_carrito_producto")),
            inverseJoinColumns = @JoinColumn(name = "idcarrito", foreignKey = @ForeignKey(name = "Fk_producto_carrito_carrito"))
    )
    private Set<Carrito> carritos = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id", foreignKey = @ForeignKey(name = "Fk_proveedor_producto"))
    @JsonBackReference
    private Proveedores proveedor;

    public Producto(String codigo, String nombre, String descripcion, String categoria, Double precio, Double stock, Proveedores proveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }
}

