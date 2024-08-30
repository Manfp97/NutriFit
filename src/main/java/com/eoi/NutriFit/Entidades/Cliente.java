package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="clientes")
@Getter
@Setter
@NoArgsConstructor  // Añadido por CPL
@AllArgsConstructor // Añadido por CPL
//@ToString // disminuye la eficiencia por las relaciones uno a muchos)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(mappedBy = "cliente")
    private Usuario usuario;

    // Se desactiva para conectar el nuevo CarroCpl
    //@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    //private Carrito carrito;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarroCPL> productos = new HashSet<>();

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private DetallesCliente detallesCliente;

    @OneToMany(mappedBy = "clienteEntrenado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientosCli;

    @OneToMany(mappedBy = "entrenadorCliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientosEntrenador;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteDieta> clienteDietasRecetado;

    @OneToMany(mappedBy = "dietista", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteDieta> clienteDietasDietista;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SesionClientes> sesionClientesCitado;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SesionClientes> sesionClientesEmpleado;
}
