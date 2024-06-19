package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
@Table(name="ClaseClientes")
public class Clase_Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado", nullable = false)
    private Integer id_empleado;

    @ManyToOne
    @Column(name = "idclase_colectiva")
    private Clase_Colectiva clase_colectiva;

    @ManyToOne
    @Column(name = "idcliente")
    private Cliente idcliente;

    }
