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
@Table(name="dietas")
//Crear onetomany hacia progresiones || crear onetoone con detalles dietas
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


    /*
    @OneToMany(mappedBy = "dieta_iddieta")
    private DetallesDieta detalles_dietas;

    @OneToOne (mappedBy = "id_progreso")
    private ProgresionDieta progresionDietas;

    @ManyToOne
    @JoinColumn(name = "id_contactodieta")
    private ClienteDieta id_contactodieta;

     */

}
