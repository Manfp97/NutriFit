package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="detalles_dietas")
public class Detalles_dietas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dieta_iddieta", nullable = false)
    private Integer dieta_iddieta;

    @Column(name = "cantidad")
    private DecimalFormat cantidad;

    @Column(name = "detalles_alimentos")
    private String detalles_alimentos;

    @ManyToOne
    @JoinColumn(name = "iddieta")
    private Dieta dieta;
}
