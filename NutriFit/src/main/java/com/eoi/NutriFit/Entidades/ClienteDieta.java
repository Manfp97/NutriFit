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
@Table(name="clientes_dietas")
//Falta el id de cliente || relacion many to one|| falta el id de dieta many to one ||
//Es necesario crear un indice unico que afecte a los campos cliente y dieta
public class ClienteDieta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

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
