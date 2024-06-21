package org.cplcursos.java.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="detalles_clientes")
public class DetallesCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "altura", nullable = false)
    private Double altura;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "bio_impedancia", length = 10)
    private Double bio_impedancia;

    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    @Column(name = "actividad_fisica", length = 50)
    private String actividad_fisica;

    @Column(name = "nivel_usuario", length = 50)
    private String nivel_usuario;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "edad")
    private Integer edad;


    @OneToOne(mappedBy = "id")
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

}