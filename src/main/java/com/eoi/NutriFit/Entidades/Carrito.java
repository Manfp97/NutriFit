package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private int preciototal;

    @OneToOne(mappedBy = "carrito")
    private Cliente clientecarrito;

    @ManyToMany
    @JoinTable(
            name = "carrito_producto",
            joinColumns = @JoinColumn(name = "idcarrito",foreignKey=@ForeignKey(name = "Fk_carritp_producto_carrito")),
            inverseJoinColumns = @JoinColumn(name = "idprodcuto",foreignKey=@ForeignKey(name = "Fk_carrito_producto_producto"))
    )
    Set<Producto> productos = new HashSet<>();

}