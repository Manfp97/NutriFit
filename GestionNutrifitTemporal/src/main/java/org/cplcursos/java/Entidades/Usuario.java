package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name="usuarios")
@Getter
@Setter
//@NoArgsConstructor
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

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

         El CASO DE USO es que un usuario puede tener varias citas. Por tanto, creamos en la Entidad usuario una
         propiedad que representa el conjunto -en este caso, como un List<>- de instancias de Albaran.

     */
    @OneToOne(mappedBy="usuario")
    private Cliente cliente;

    @OneToOne(mappedBy="usuario")
    private Roles roles;

    // Constructores
    public Usuario() {
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