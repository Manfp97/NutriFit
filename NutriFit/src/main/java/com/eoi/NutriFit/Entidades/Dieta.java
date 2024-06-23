package com.eoi.NutriFit.Entidades;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Set;

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

    @Lob
    private byte [] recursos_multimedia;

    @Column (name = "planificacion_frecuencia")
    private String planificacion_frecuencia;



    @OneToMany
    private Set<DetallesDieta> detalles_dietas;

    @OneToOne
    private ProgresionDieta progresionDietas;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcontactodieta",foreignKey=@ForeignKey(name = "Fk_contactodieta_dieta"))
    private DetallesDieta detalles_contactodieta;


}