package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
@Table(name="ClaseClientes")
public class SesionClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado", nullable = false)
    private Integer id_empleado;



    @ManyToOne
    @Column(name = "idclase_colectiva")
    private SesionColectiva sesion_colectiva;

    @ManyToOne
    @Column(name = "idcliente")
    private Cliente idcliente;

     */

    }
