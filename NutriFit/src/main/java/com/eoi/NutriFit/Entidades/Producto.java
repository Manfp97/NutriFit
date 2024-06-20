package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String codigo;
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "producto_carrito",
            joinColumns = @JoinColumn(name = "idproducto",foreignKey=@ForeignKey(name = "Fk_producto_carrito_producto")),
            inverseJoinColumns = @JoinColumn(name = "idcarrito",foreignKey=@ForeignKey(name = "Fk_producto_carrito_carrito"))
    )
    Set<Carrito> carritos = new HashSet<>();


}