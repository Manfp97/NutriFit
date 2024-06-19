package org.cplcursos.java.Entidades;

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
@Table(name="proveedores")
public class Proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproveedores", nullable = false)
    private Integer idproveedores;


    @ManyToOne
    @Column(name = "id_producto")
    private Tienda id_producto;

}



