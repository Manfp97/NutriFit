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

import java.util.*;

@Service
public class UsuarioSecurityImpl implements IUsuarioServicio, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public String getEncodedPassword(Usuario usuario) {
        String passwd = usuario.getPassword();
        String encodedPasswod = passwordEncoder.encode(passwd);
        return encodedPasswod;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername email : " + usuario);
        Optional<Usuario> usuario1 = usuarioRepository.findUsuarioByUsernameAndActivoTrue(usuario);
        if (usuario1.isEmpty()){
           //Cogemos el usuario anonimo
            Optional<Usuario> usuarioAnonimo = usuarioRepository.findUsuarioByUsernameAndActivoTrue("anonimo");
            System.out.println("loadUserByUsername usuario : " + usuarioAnonimo.get().getUsername());
            org.springframework.security.core.userdetails.User springUser = null;

            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(usuarioAnonimo.get().getRol().getNombreRol()));
            springUser = new org.springframework.security.core.userdetails.User(
                    usuarioAnonimo.get().getUsername(),
                    usuarioAnonimo.get().getPassword(),
                    ga);
            return springUser;
        }
        else {
            System.out.println("loadUserByUsername usuario : " + usuario1.get().getUsername());

            org.springframework.security.core.userdetails.User springUser = null;

            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(usuario1.get().getRol().getNombreRol()));
            springUser = new org.springframework.security.core.userdetails.User(
                    usuario,
                    usuario1.get().getPassword(),
                    ga);
            return springUser;
        }
    }
}


