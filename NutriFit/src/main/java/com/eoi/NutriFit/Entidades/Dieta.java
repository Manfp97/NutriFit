package com.eoi.NutriFit.Entidades;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="dieta")
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column (name = "objetivos")
    private String objetivos;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "recursos_multimedia")
    private Blob recursos_multimedia;

    @Column (name = "planificacion_frecuencia")
    private String planificacion_frecuencia;



    @OneToMany
    private org.cplcursos.java.Entidades.DetallesDieta detalles_dietas;

    @OneToOne
    private ProgresionDieta progresionDietas;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcontactodieta",foreignKey=@ForeignKey(name = "Fk_contactodieta_dieta"))
    private org.cplcursos.java.Entidades.DetallesDieta detalles_contactodieta;


}