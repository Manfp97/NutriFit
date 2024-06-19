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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identrenamiento", nullable = false)
    private Integer identrenamiento;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "recursos_multimedia")
    private Blob recursosMultimedia;

    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    @Column(name = "objetivos")
    private String objetivos;

    @Column(name = "planificacion_frecuencia")
    private String planificacionFrecuencia;

    @ManyToOne
    @Column(name = "idcontacto_entrenamiento")
    private Contacto_entrenamiento idcontactoentrenamiento;

    @OneToMany(mappedBy = "entrenamientos_identrenamiento")
    private Progresiones_entrenamientos entrenamientos_identrenamiento;

    @ManyToOne
    @Column(name = "id_detalles")
    private Detalles_entrenamientos id_detalles;

}
