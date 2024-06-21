package org.cplcursos.java.Entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Entrenamiento")
public class Entrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "categoria")
    private String categoria;

    @Lob
    private byte [] recursos_multimedia;

    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    @Column(name = "objetivos")
    private String objetivos;

    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddetallesentrenamiento",foreignKey=@ForeignKey(name = "Fk_detallesentrenamiento_entrenamiento"))
    private DetallesEntrenamiento DetallesEntrenamiento;

    @OneToMany
    private ProgresionesEntrenamiento entrenamientos_identrenamiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idprogresionentrenamiento",foreignKey=@ForeignKey(name = "Fk_progresionentrenamiento_entrenamiento"))
    private ProgresionesEntrenamiento ProgresionesEntrenamiento;



}