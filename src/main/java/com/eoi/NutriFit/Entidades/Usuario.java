package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * La clase <code>Usuario</code> representa una entidad que define los usuarios del sistema.
 * Cada usuario tiene un nombre de usuario, contraseña, estado de activación y relaciones con otras entidades
 * como <code>Cliente</code>, <code>Roles</code> y <code>DetalleUsuario</code>.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que se mapea a una tabla en la base de datos,
 * denominada <code>usuarios</code>.</p>
 *
 * <p>Se utilizan las anotaciones de Lombok <code>@Getter</code>, <code>@Setter</code> y <code>@ToString</code>
 * para generar automáticamente los métodos getter, setter y un método toString, respectivamente.
 * La anotación <code>@ToString.Exclude</code> se utiliza para evitar la inclusión de referencias cíclicas
 * en el método toString.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString(exclude = {"detalleUsuario", "rol"})
public class Usuario {

    /**
     * Identificador único del usuario.
     * Este campo es autogenerado con la estrategia <code>GenerationType.IDENTITY</code> y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre de usuario que el usuario utilizará para iniciar sesión.
     * Este campo es obligatorio y tiene una longitud máxima de 100 caracteres.
     */
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    /**
     * Contraseña del usuario, almacenada de manera segura.
     * Este campo es obligatorio y tiene una longitud máxima de 250 caracteres.
     */
    @Column(name = "password", nullable = false, length = 250)
    private String password;

    /**
     * Estado de activación del usuario. 
     * Este campo indica si el usuario está activo (<code>true</code>) o inactivo (<code>false</code>).
     * El valor por defecto es <code>true</code>.
     */
    @Column(name = "activo")
    private boolean activo = true;

    /**
     * Relación uno a uno con la entidad <code>Cliente</code>.
     * Representa el cliente asociado a este usuario.
     */
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    /**
     * Relación muchos a uno con la entidad <code>Roles</code>.
     * Representa el rol asignado a este usuario, que es obligatorio.
     */
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Roles rol;

    /**
     * Relación uno a uno con la entidad <code>DetalleUsuario</code>.
     * Contiene detalles adicionales del usuario.
     * Esta relación se gestiona con cascada total.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_usuario_id")
    private DetalleUsuario detalleUsuario;

}
