package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

        /*
         La relación @OneToMany permite que una entidad se relacione con múltiples instancias de otra.
         La propiedad que define la relación debe anotarse con @OneToMany y debe indicar mediante la cláusula
         "mappedBy" cuál es la propiedad de la entidad relacionada que sirve de nexo.

         @OneToMany es la otra parte de la relación @ManyToOne

         El CASO DE USO es que un cliente puede tener varias citas. Por tanto, creamos en la Entidad Cliente una
         propiedad que representa el conjunto -en este caso, como un List<>- de instancias de Albaran.

     */

    @OneToOne(mappedBy ="clientes")
    private Usuario idusuario;

    @OneToOne(mappedBy = "clientes")
    private Carrito carrito;

    @OneToOne(mappedBy = "idcontacto_entrenamiento")
    private Contacto_entrenamiento idcontacto_entrenamiento;


    // Constructores
    public Cliente() {

    }

}