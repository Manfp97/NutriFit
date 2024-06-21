package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="progresiones_dietas")
public class ProgresionDieta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "peso_incial")
    private Integer peso_incial;

    @Column(name = "peso_objetivo")
    private Integer peso_objetivo;


    /*
    @OneToOne (mappedBy = "iddieta")
    private Dieta dieta;


     */

}
