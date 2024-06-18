package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente", nullable = false)
    private Integer idcliente;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "direccion", length = 80)
    private String direccion;

    @Column(name = "dni", length = 10)
    private String dni;

    @Column(name = "edad")
    private Byte edad;

    @Column(name = "emilio", length = 50)
    private String emilio;

    @Column(name = "creadoEl")
    private LocalDateTime creadoEl;

    @Column(name = "ultimaModificacion")
    private LocalDateTime ultimaModificacion;

    @OneToOne(mappedBy = "idusuario")
    private Usuario usuario;

    // Constructores

    // Se elimina el constructor por redundancia

    @PrePersist
    public void prePersist() {
        this.creadoEl = LocalDateTime.now();
    }

    @PreUpdate
    public void preMerge() {
        this.ultimaModificacion = LocalDateTime.now();
    }
}
