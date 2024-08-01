package com.eoi.NutriFit.Entidades;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    @Column(name = "nombre")
    private String nombre;

    @Column (name = "objetivos")
    private String objetivos;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "descripcion")
    private String descripcion;

    @Lob
    private byte [] recursos_multimedia;

    @Column (name = "planificacion_frecuencia")
    private String planificacion_frecuencia;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;


    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    private Set<DetallesDieta> detalles_dietas;


    @OneToMany(mappedBy = "dieta")
    private Set<ProgresionDieta> progresionDietas;


    public Dieta(String nombre, String objetivos, String categoria, byte[] recursosMultimedia,
                 String planificacionFrecuencia, String descripcion) {

        this.nombre = nombre;
        this.objetivos = objetivos;
        this.categoria = categoria;
        this.recursos_multimedia = recursosMultimedia;
        this.planificacion_frecuencia = planificacionFrecuencia;
        this.descripcion = descripcion;
    }



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcontactodieta", foreignKey = @ForeignKey(name = "Fk_contactodieta_dieta"))
    private DetallesDieta contactoDieta;



}