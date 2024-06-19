package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="tienda")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Integer id_producto;

    @Column(name = "productocol")
    private String productocol;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio")
    private DecimalFormat precio;

    @Column(name = "stock")
    private DecimalFormat stock;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito id_carrito;

    @OneToMany(mappedBy = "idproveedores")
    private Proveedores idproveedores;

}
