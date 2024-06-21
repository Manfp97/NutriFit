package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="detalles_dietas")
public class DetallesDieta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cantidad")
    private DecimalFormat cantidad;

    @Column(name = "detalles_alimentos")
    private String detalles_alimentos;

    /*
    @ManyToOne
    @JoinColumn(name = "iddieta")
    private Dieta dieta;

     */
}
