package com.eoi.NutriFit.Security;

import com.eoi.NutriFit.Entidades.Roles;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementación del servicio de seguridad para usuarios, que proporciona funcionalidades
 * para codificar contraseñas y cargar usuarios para la autenticación de Spring Security.
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Service
public class UsuarioSecurityImpl implements IUsuarioServicio, UserDetailsService {

    /**
     * Repositorio de usuarios inyectado mediante Autowired.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Codificador de contraseñas BCrypt inyectado mediante Autowired.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Codifica la contraseña de un usuario utilizando el codificador BCrypt.
     *
     * @param usuario El usuario cuya contraseña se desea codificar.
     * @return La contraseña del usuario codificada en un formato seguro.
     */
    @Override
    public String getEncodedPassword(Usuario usuario) {
        String plainPassword = usuario.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        return encodedPassword;
    }

    /**
     * Carga un usuario desde la base de datos por su nombre de usuario para la autenticación de Spring Security.
     *
     * Si el usuario no se encuentra, se carga un usuario anónimo con las
     * autorizaciones correspondientes.
     *
     * @param username El nombre de usuario a cargar.
     * @return Un objeto UserDetails que representa el usuario cargado.
     * @throws UsernameNotFoundException Si el usuario no se encuentra y no existe un usuario anónimo configurado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername email : " + username);

        // Buscar al usuario por nombre de usuario y activo
        Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsernameAndActivoTrue(username);

        if (optionalUsuario.isEmpty()) {
            // Si no se encuentra el usuario, buscar usuario anónimo
            Optional<Usuario> optionalUsuarioAnonimo = usuarioRepository.findUsuarioByUsernameAndActivoTrue("anonimo");
            if (optionalUsuarioAnonimo.isEmpty()) {
                throw new UsernameNotFoundException("Usuario no encontrado y no hay un usuario anónimo configurado");
            }
            Usuario usuarioAnonimo = optionalUsuarioAnonimo.get();
            System.out.println("loadUserByUsername usuario anónimo: " + usuarioAnonimo.getUsername());

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(usuarioAnonimo.getRol().getNombreRol()));

            return new org.springframework.security.core.userdetails.User(
                    usuarioAnonimo.getUsername(),
                    usuarioAnonimo.getPassword(),
                    grantedAuthorities);
        }

        Usuario usuario = optionalUsuario.get();
        System.out.println("loadUserByUsername usuario: " + usuario.getUsername());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(usuario.getRol().getNombreRol()));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                grantedAuthorities);
    }
}