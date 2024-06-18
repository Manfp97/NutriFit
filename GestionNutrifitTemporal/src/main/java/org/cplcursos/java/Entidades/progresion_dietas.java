package org.cplcursos.java.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class progresion_dietas {
    @Id
    @Column(name = "id_progreso")
    private Integer id_progreso;

    @Column(name = "peso_incial")
    private Integer peso_incial;

    @Column(name = "peso_objetivo")
    private Integer peso_objetivo;

}
