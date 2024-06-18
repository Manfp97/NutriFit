package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="clientes")
@Getter
@Setter
//@NoArgsConstructor
@ToString
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

    @Column(name="creadoel")
    private LocalDateTime creadoEl;

    @Column(name="ultimamodificacion")
    private LocalDateTime ultimaModificacion;

    /*
         La relación @OneToMany permite que una entidad se relacione con múltiples instancias de otra.
         La propiedad que define la relación debe anotarse con @OneToMany y debe indicar mediante la cláusula
         "mappedBy" cuál es la propiedad de la entidad relacionada que sirve de nexo.

         @OneToMany es la otra parte de la relación @ManyToOne

         El CASO DE USO es que un cliente puede tener varias citas. Por tanto, creamos en la Entidad Cliente una
         propiedad que representa el conjunto -en este caso, como un List<>- de instancias de Albaran.

     */


    @OneToOne(mappedBy ="idusuario")
    private Usuario idusuario;


    // Constructores
    public Cliente() {
        this.ultimaModificacion = LocalDateTime.now();
    }



    @PrePersist
    public void prePersist() {
        this.creadoEl = LocalDateTime.now();
    }

    @PreUpdate
    public void preMerge() {
        this.ultimaModificacion = LocalDateTime.now();
    }


}