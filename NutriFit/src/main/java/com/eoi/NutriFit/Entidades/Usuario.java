package com.eoi.NutriFit.Entidades;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Basic(optional = false)
    private boolean activo = true;



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
    @OneToOne(mappedBy = "usuarioCliente")
    private Cliente clienteUsuario;

    @OneToOne()
    private Roles roles;

    @OneToOne()
    private DetalleUsuario detalleUsuario;




}