package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="contacto_dietas")
public class ClienteDieta {
    @Id
    @Column(name = "idcontacto_dieta")
    private int idcontacto_dieta;

    @Column(name = "detalles_dietas")
    private String detalles_dietas;

    @Column(name = "horario")
    private LocalDateTime horario;


    /*
    @OneToMany (mappedBy = "iddieta")
    private Dieta dieta;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    */

}
