
package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Progresiones_entrenamientos")
public class ProgresionesEntrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "repeticiones")
    private int repeticiones;

    @Column(name = "series")
    private int series;

    @Column(name = "peso")
    private int peso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEntrenamiento")
    private Entrenamiento entrenamiento;


}
