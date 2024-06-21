package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre_rol", nullable = false, length = 45)
    private String nombreRol;


    /*
         La relación @OneToMany permite que una entidad se relacione con múltiples instancias de otra.
         La propiedad que define la relación debe anotarse con @OneToMany y debe indicar mediante la cláusula
         "mappedBy" cuál es la propiedad de la entidad relacionada que sirve de nexo.

         @OneToMany es la otra parte de la relación @ManyToOne

         El CASO DE USO es que un usuario puede tener varias citas. Por tanto, creamos en la Entidad usuario una
         propiedad que representa el conjunto -en este caso, como un List<>- de instancias de Albaran.

     */
    @OneToOne(mappedBy="roles")
    private Usuario usuario;







}