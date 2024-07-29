package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="usuarios")
@Getter
@Setter
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

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
}
