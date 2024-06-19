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
@Table(name="Contacto_entrenamiento")
public class Contacto_entrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcontacto_entrenamiento", nullable = false)
    private Integer idcontacto_entrenamiento;

    @Column(name = "detalles_ejercicios")
    private String detalles_ejercicios;

    @Column(name = "recursos_multimedia")
    private Blob recursos_multimedia;

    @Column(name = "planificacion_frecuencia")
    private String planificacion_frecuencia;

    @ManyToOne
    @Column(name = "idcliente")
    private Cliente idcliente;

    @OneToOne(mappedBy = "idcliente")
    private Cliente carrito_idcarrito;

}
