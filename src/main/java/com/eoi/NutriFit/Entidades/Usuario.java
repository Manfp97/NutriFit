package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario en el sistema.
 * <p>
 * La entidad {@code Usuario} almacena información sobre los usuarios, incluyendo sus credenciales de inicio de sesión,
 * estado de activación, token de restablecimiento de contraseña, y relaciones con otros objetos como {@code Cliente},
 * {@code Roles} y {@code DetalleUsuario}.
 * </p>
 *
 * <p>
 * Autores: Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * </p>
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"detalleUsuario", "rol"})
public class Usuario {

    /**
     * Identificador único del usuario.
     * <p>
     * Es una columna autoincrementable en la base de datos.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambiado a IDENTITY
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre de usuario para el inicio de sesión.
     * <p>
     * Es un campo obligatorio con un límite de longitud de 100 caracteres.
     * </p>
     */
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    /**
     * Contraseña del usuario, almacenada de forma segura.
     * <p>
     * Es un campo obligatorio con un límite de longitud de 250 caracteres.
     * </p>
     */
    @Column(name = "password", nullable = false, length = 250)
    private String password;

    /**
     * Indica si el usuario está activo o no.
     * <p>
     * El valor predeterminado es {@code true}.
     * </p>
     */
    @Column(name = "activo")
    private boolean activo = true;

    /**
     * Token de restablecimiento de contraseña para el usuario.
     * <p>
     * Este campo es opcional y se utiliza para gestionar el proceso de recuperación de contraseña.
     * </p>
     */
    @Column(name = "reset_token")
    private String resetToken;

    /**
     * Fecha y hora de expiración del token de restablecimiento de contraseña.
     * <p>
     * Este campo es opcional y se utiliza para verificar la validez del token.
     * </p>
     */
    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    /**
     * Cliente asociado al usuario.
     * <p>
     * Establece una relación uno a uno con la entidad {@code Cliente}.
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    /**
     * Rol del usuario en el sistema.
     * <p>
     * Establece una relación muchos a uno con la entidad {@code Roles}.
     * Este campo es obligatorio.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Roles rol;

    /**
     * Detalle adicional del usuario.
     * <p>
     * Establece una relación uno a uno con la entidad {@code DetalleUsuario}.
     * </p>
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_usuario_id")
    private DetalleUsuario detalleUsuario;
}
