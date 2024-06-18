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
@Table(name="piezas")
public class Pieza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String codigo;
    private String nombre;

    @ManyToMany(mappedBy = "piezas")
    private Set<Proveedor> proveedores = new HashSet<>();

    public Pieza(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }

}