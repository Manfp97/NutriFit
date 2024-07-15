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
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "preciototal")
    private int preciototal;

    // Establishing bidirectional one-to-one relationship with Cliente
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Establishing bidirectional many-to-many relationship with Producto
    @ManyToMany
    @JoinTable(
            name = "carrito_producto",
            joinColumns = @JoinColumn(name = "idcarrito", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idproducto", referencedColumnName = "id")
    )
    private Set<Producto> productos = new HashSet<>();

    // Constructors, getters, setters, and other methods as needed
}
