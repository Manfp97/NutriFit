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
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String codigo;
    private String nombre;

    @ManyToMany(mappedBy = "producto")
    private Set<Proveedores> proveedores = new HashSet<>();

    public Producto(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }

}