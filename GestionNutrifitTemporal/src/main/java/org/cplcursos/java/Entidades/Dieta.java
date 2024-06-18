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
@Table(name="Dieta")
public class Dieta {
    @Id
    @Column(name = "id", nullable = false)
    private Integer iddieta;

    @Column (name = "objetivos")
    private String objetivos;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "recursos_multimedia")
    private Blob recursos_multimedia;

    @Column (name = "planificacion_frecuencia")
    private String planificacion_frecuencia;

    @OneToMany(mappedBy = "dieta_iddieta")
    private detalles_dietas detalles_dietas;

    @OneToOne (mappedBy = "id_progreso")
    private progresion_dietas progresionDietas;

    @ManyToOne
    @JoinColumn(name = "id_contactodieta")
    private contacto_dietas id_contactodieta;

}
