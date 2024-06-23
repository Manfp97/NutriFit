package com.eoi.NutriFit.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "estado_fisico_anterior")
    private String estado_fisico_anterior;

    @Column(name = "progresion_actual")
    private String progresion_actual;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idprogresion_entrenamiento",foreignKey=@ForeignKey(name = "Fk_progresion_entrenamiento"))
    private Entrenamiento entrenamiento;


}