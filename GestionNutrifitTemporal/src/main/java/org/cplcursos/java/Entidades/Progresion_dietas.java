package org.cplcursos.java.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="progresion_dietas")
public class Progresion_dietas {
    @Id
    @Column(name = "id_progreso")
    private Integer id_progreso;

    @Column(name = "peso_incial")
    private Integer peso_incial;

    @Column(name = "peso_objetivo")
    private Integer peso_objetivo;

    @OneToOne (mappedBy = "iddieta")
    private Dieta dieta;


}
