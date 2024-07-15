package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="detalleusuario")
@Getter
@Setter
@ToString
public class DetalleUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed to IDENTITY for auto-increment
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 250)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 500)
    private String apellidos;

    @Column(name = "direccion", length = 80)
    private String direccion;

    @Column(name = "dni", length = 10)
    private String dni;

    @Column(name = "email", length = 50)
    private String email;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
