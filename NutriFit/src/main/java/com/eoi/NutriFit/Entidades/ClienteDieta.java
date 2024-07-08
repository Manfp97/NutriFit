package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="contacto_dietas")
public class ClienteDieta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "detalles_dietas")
    private String detalles_dietas;

    @Column(name = "horario")
    private LocalDateTime horario;


    @OneToMany
    private Set<Dieta> dieta;

    @OneToOne(mappedBy = "id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente idcliente;


}