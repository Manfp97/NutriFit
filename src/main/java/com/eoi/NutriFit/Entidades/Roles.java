package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * La clase <code>Roles</code> representa la entidad que define los roles de usuario dentro del sistema.
 * Cada rol puede ser asignado a múltiples usuarios, estableciendo la relación entre usuarios y sus permisos o funciones.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA y será mapeada a una
 * tabla en la base de datos. La tabla correspondiente en la base de datos se denomina <code>roles</code>.</p>
 *
 * <p>Se utilizan anotaciones de Lombok como <code>@Getter</code>, <code>@Setter</code>, <code>@NoArgsConstructor</code>,
 * <code>@ToString</code> y <code>@AllArgsConstructor</code> para generar automáticamente los métodos getter, setter,
 * un constructor sin argumentos, un método <code>toString()</code> personalizado, y un constructor con todos los
 * argumentos.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Roles {

    /**
     * Identificador único para cada rol.
     * Este campo es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre descriptivo del rol.
     * Este campo es obligatorio y tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "nombre_rol", nullable = false, length = 45)
    private String nombreRol;

    /**
     * Conjunto de usuarios que están asociados a este rol.
     * La relación está mapeada por el campo <code>rol</code> en la clase <code>Usuario</code>.
     * Se excluye del método <code>toString()</code> para evitar bucles infinitos en la representación de la cadena.
     */
    @OneToMany(mappedBy = "rol")
    @ToString.Exclude
    private Set<Usuario> usuarios;
}
