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
@Table(name="carrito")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito", nullable = false)
    private Integer id_carrito;

    @Column(name = "lista_articulos")
    private String lista_articulos;

    @Column(name = "precio_total")
    private DecimalFormat precio_total;

    @Column(name = "pagado")
    private boolean pagado;

    @OneToOne(mappedBy = "idcliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "id_producto")
    private Tienda tienda;

}
