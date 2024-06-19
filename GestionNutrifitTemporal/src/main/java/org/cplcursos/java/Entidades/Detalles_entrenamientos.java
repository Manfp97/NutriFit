package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Detalles_entrenamientos")
public class Detalles_entrenamientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalles", nullable = false)
    private Integer id_detalles;

    @Column(name = "volumen_repeticiones")
    private Integer volumen_repeticiones;

    @Column(name = "descansos")
    private DecimalFormat descansos;

    @Column(name = "detalles_ejercicios")
    private String detalles_ejercicios;

    @OneToMany(mappedBy = "identrenamiento")
    private Entrenamiento identrenamiento;

}
