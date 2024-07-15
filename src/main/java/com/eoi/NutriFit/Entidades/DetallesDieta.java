package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal cantidad;

    @Column(name = "detalles_alimentos")
    private String detalles_alimentos;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddieta", foreignKey = @ForeignKey(name = "Fk_detalles_dieta"))
    private Dieta dieta;


}