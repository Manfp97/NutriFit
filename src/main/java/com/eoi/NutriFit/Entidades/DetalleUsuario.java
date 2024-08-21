package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * La clase DetalleUsuario representa la entidad que almacena información detallada sobre un usuario,
 * incluyendo datos personales y de contacto.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA que será mapeada
 * a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code>, y <code>@ToString</code>
 * para generar automáticamente los métodos getter, setter, y el método <code>toString</code>, respectivamente.
 * El método <code>toString</code> excluye el campo <code>usuario</code> para evitar posibles problemas de recursión.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>detalleusuario</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Table(name="detalleusuario")
@Getter
@Setter
@ToString(exclude = "usuario")
public class DetalleUsuario {

    /**
     * Identificador único de la entidad DetalleUsuario.
     * Este campo es autogenerado con la estrategia de identidad y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre del usuario. Este campo no puede ser nulo y su longitud máxima es de 250 caracteres.
     */
    @Column(name = "nombre", nullable = false, length = 250)
    private String nombre;

    /**
     * Apellidos del usuario. Este campo no puede ser nulo y su longitud máxima es de 500 caracteres.
     */
    @Column(name = "apellidos", nullable = false, length = 500)
    private String apellidos;

    /**
     * Dirección del usuario. Este campo puede ser nulo y su longitud máxima es de 80 caracteres.
     */
    @Column(name = "direccion", length = 80)
    private String direccion;

    /**
     * Documento Nacional de Identidad (DNI) del usuario. Este campo puede ser nulo y su longitud máxima es de 10 caracteres.
     */
    @Column(name = "dni", length = 10)
    private String dni;

    /**
     * Dirección de correo electrónico del usuario. Este campo puede ser nulo y su longitud máxima es de 50 caracteres.
     */
    @Column(name = "email", length = 50)
    private String email;

    /**
     * Relación uno a uno con la entidad <code>Usuario</code>. Representa al usuario que está asociado a estos detalles.
     * La propiedad <code>detalleUsuario</code> en la entidad <code>Usuario</code> es la que mapea esta relación.
     */
    @OneToOne(mappedBy = "detalleUsuario")
    private Usuario usuario;
}
