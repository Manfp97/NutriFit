package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name="usuarios")
@Getter
@Setter
//@NoArgsConstructor
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Basic(optional = false)
    private boolean activo = true;


    public Usuario(Integer id, String nombreUsuario, String password, boolean activo, Cliente clienteUsuario, Roles roles) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.activo = activo;
        this.clienteUsuario = clienteUsuario;
        this.roles = roles;
    }

    @OneToOne()
    private Cliente clienteUsuario;

    @OneToOne()
    private Roles roles;





}