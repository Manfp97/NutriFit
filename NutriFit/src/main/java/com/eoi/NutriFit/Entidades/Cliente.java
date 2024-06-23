package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.eoi.NutriFit.Entidades.Cliente;

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

    @OneToOne(mappedBy ="clienteUsuario")
    private Usuario usuario;

    @OneToOne()
    private Carrito carrito;

    @OneToOne()
    private DetallesCliente detallesCliente;

    //Usuario cliente entrenado por Usuario entrenador
    @OneToMany(mappedBy = "clienteentrenado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientos_cli;

    //Usuario entrenador entrena a cliente
    @OneToMany(mappedBy = "entrenadorcliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientos_entrenador;

    //Usuario cliente recetado por Usuario dietista
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteDieta> clienteDietas_recetado;

    //Usuario dietista receta a cliente
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteDieta> clienteDietas_dietista;

    //Usuario cliente citado por Usuario empleado
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SesionClientes> sesionClientes_citado;

    //Usuario empleado cita a cliente
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SesionClientes> sesionClientes_empleado;

}