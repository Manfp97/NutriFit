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

    private String nombre;

    @ManyToMany
    @JoinTable(name="proveedoresproductos",
            joinColumns={@JoinColumn(name="id_Prov")},
            inverseJoinColumns={@JoinColumn(name="id_productos")})
    private Set<producto> productos = new HashSet<>();

    // Helpers
    public void nuevoProducto(Producto pr) {
        this.producto.add(pr);
        pr.getProveedores().add(this);
    }

    public void eliminarProducto(Producto pr){
        this.producto.remove(pr);
        pr.getProveedores().remove(this);
    }

    // Auxiliares
    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", piezas=" + piezas +
                '}';
    }
}