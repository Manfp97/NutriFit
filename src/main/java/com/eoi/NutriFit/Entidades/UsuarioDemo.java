package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Clase que representa a un usuario en el sistema.
 * Implementa la interfaz {@link UserDetails} de Spring Security para integrar la seguridad
 * y el control de acceso en la aplicación.
 *
 * <p>
 * Utiliza anotaciones de Lombok para generar automáticamente los métodos getter, setter, constructores
 * con y sin argumentos. Además, utiliza anotaciones de JPA para mapear la entidad a una tabla en la base de datos.
 * </p>
 *
 * <p>
 * Ejemplo de uso:
 * <pre>{@code
 * UsuarioDemo usuario = new UsuarioDemo();
 * usuario.setNombreDeUsuario("ateixei");
 * usuario.setContrasenia("password123");
 * // otros setters...
 * }</pre>
 * </p>
 *
 * <p>
 * Esta clase también se encarga de proporcionar los roles y las autoridades necesarias
 * para Spring Security mediante la implementación de los métodos de la interfaz {@link UserDetails}.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UsuarioDemo implements UserDetails, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Nombre de usuario utilizado para la autenticación.
     */
    private String nombreDeUsuario; //ateixei

    /**
     * Nombre de pila del usuario.
     */
    private String nombreDePila; //alejandro

    /**
     * Apellido del usuario.
     */
    private String apellidoPila;

    /**
     * Correo electrónico del usuario.
     */
    private String correoPila;

    /**
     * Contraseña del usuario, utilizada para la autenticación.
     */
    private String contrasenia;

    /**
     * Rol del usuario, determina las autoridades y permisos.
     */
    private String rol;

    /**
     * Retorna las autoridades otorgadas al usuario. Este método es utilizado por Spring Security
     * para realizar la autorización de los usuarios en la aplicación.
     *
     * @return una colección de {@link GrantedAuthority} que representa los roles y permisos del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(rol));
        return authorities;
    }

    /**
     * Retorna la contraseña del usuario. Este método es utilizado por Spring Security
     * durante el proceso de autenticación.
     *
     * @return la contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return contrasenia;
    }

    /**
     * Retorna el nombre de usuario utilizado para la autenticación.
     * Este método es utilizado por Spring Security durante el proceso de autenticación.
     *
     * @return el nombre de usuario.
     */
    @Override
    public String getUsername() {
        return nombreDeUsuario;
    }

    /**
     * Indica si la cuenta del usuario ha expirado. Este método es utilizado por Spring Security
     * para determinar si la cuenta del usuario está habilitada.
     *
     * @return true si la cuenta del usuario no ha expirado, false en caso contrario.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada. Este método es utilizado por Spring Security
     * para determinar si la cuenta del usuario está bloqueada.
     *
     * @return true si la cuenta del usuario no está bloqueada, false en caso contrario.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado. Este método es utilizado por Spring Security
     * para determinar si las credenciales del usuario están vigentes.
     *
     * @return true si las credenciales del usuario no han expirado, false en caso contrario.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está habilitada. Este método es utilizado por Spring Security
     * para determinar si la cuenta del usuario está habilitada.
     *
     * @return true si la cuenta del usuario está habilitada, false en caso contrario.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
