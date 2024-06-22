package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Entity
@Table(name="clientes")
@Getter
@Setter
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    private Usuario usuarioCliente;

    @OneToOne()
    private Carrito carrito;

    //Usuario cliente entrenado por Usuario entrenador
    @OneToMany(mappedBy = "clienteentrenado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientos_cli;

    //Usuario entrenador entrena a cliente
    @OneToMany(mappedBy = "entrenadorcliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientos_entrenador;

}