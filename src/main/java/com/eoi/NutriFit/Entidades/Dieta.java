package com.eoi.NutriFit.Entidades;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    private Set<DetallesDieta> detalles_dietas;


    @OneToMany(mappedBy = "dieta")
    private Set<ProgresionDieta> progresionDietas;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcontactodieta", foreignKey = @ForeignKey(name = "Fk_contactodieta_dieta"))
    private DetallesDieta contactoDieta;



}