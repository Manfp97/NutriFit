package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "detalles_clientes")
public class DetallesCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "altura", nullable = false)
    private Double altura;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "bio_impedancia")
    private Double bioImpedancia;

    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    @Column(name = "actividad_fisica")
    private String actividadFisica;

    @Column(name = "nivel_usuario")
    private String nivelUsuario;

    @Column(name = "genero")
    private String genero;

    @Column(name = "edad")
    private Integer edad;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
