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
//@ToString
@Table(name="ClaseColectiva")
public class SesionColectiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clase_colectiva", nullable = false)
    private Integer id_clase_colectiva;

    @Column(name = "tipo_clase" , nullable = false , length = 45)
    private String tipo_clase;

    @Column(name="horario_entrada")
    private LocalDateTime horario_entrada;

    @Column(name="horario_salida")
    private LocalDateTime horario_salida;

    @Column(name = "limite_reservas")
    private Byte limite_reservas;

    /*
    @OneToMany(mappedBy = "id")
    private SesionClientes id;

     */
}
