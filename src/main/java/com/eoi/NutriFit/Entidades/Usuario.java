package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuarios")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "activo")
    private boolean activo = true;

//    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Cliente clienteUsuario;

    @OneToOne
    @JoinColumn(name = "cliente_id") // Assuming this is how it's mapped
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Roles rol;

    @OneToOne(mappedBy = "usuario")
    private DetalleUsuario detalleUsuario;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

}
