package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
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

  /* Crear relación M/N con productos

   */

    @OneToOne(mappedBy = "carrito")
    private Cliente clientecarrito;

    @ManyToMany()
    private Set<Producto> productoSet;

}
