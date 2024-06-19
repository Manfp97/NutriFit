package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name="usuarios")
@Getter
@Setter
//@NoArgsConstructor
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "contraseña", length = 25)
    private String direccion;

    @Column(name = "activo")
    private Integer activo;

    @Column(name = "email", length = 50)
    private String email;


    /*
         La relación @OneToMany permite que una entidad se relacione con múltiples instancias de otra.
         La propiedad que define la relación debe anotarse con @OneToMany y debe indicar mediante la cláusula
         "mappedBy" cuál es la propiedad de la entidad relacionada que sirve de nexo.

         @OneToMany es la otra parte de la relación @ManyToOne

         El CASO DE USO es que un usuario puede tener varias citas. Por tanto, creamos en la Entidad usuario una
         propiedad que representa el conjunto -en este caso, como un List<>- de instancias de Albaran.

     */
    @OneToOne(mappedBy="idcliente")
    private Cliente cliente;

    @OneToOne(mappedBy="usuario")
    private Roles roles;

    // Constructores
    public Usuario() {

    }

}