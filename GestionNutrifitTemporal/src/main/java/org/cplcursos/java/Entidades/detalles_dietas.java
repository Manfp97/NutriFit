package org.cplcursos.java.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.text.DecimalFormat;

public class detalles_dietas {
    @Column(name = "cantidad")
    private DecimalFormat cantidad;

    @Column(name = "detalles_alimentos")
    private String detalles_alimentos;
}
