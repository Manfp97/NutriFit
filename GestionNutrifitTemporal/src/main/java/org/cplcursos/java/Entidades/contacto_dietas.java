package org.cplcursos.java.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;

public class contacto_dietas {
    @Id
    @Column(name = "idcontacto_dieta")
    private int idcontacto_dieta;

    @Column(name = "detalles_dietas")
    private String detalles_dietas;

    @Column(name = "horario")
    private LocalDateTime horario;

    @OneToMany (mappedBy = "idcontacto_dieta")
    private <List>Dieta dieta;

    @
}
