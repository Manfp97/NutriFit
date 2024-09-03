package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * La clase {@code Cliente} representa a un cliente en la aplicación NutriFit.
 * Esta entidad está mapeada a la tabla "clientes" en la base de datos.
 *
 * <p>La clase {@code Cliente} tiene varias relaciones uno a uno y uno a muchos con otras entidades
 * como {@code Usuario}, {@code Carrito}, {@code DetallesCliente}, {@code ClienteEntrenamiento},
 * {@code ClienteDieta} y {@code SesionClientes}. Estas relaciones están correctamente mapeadas
 * utilizando anotaciones de JPA.</p>
 *
 * <p>Se utilizan las anotaciones de Lombok {@code @Getter}, {@code @Setter} y {@code @ToString}
 * para generar automáticamente los métodos getter, setter y el método {@code toString()}.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "clientes")
@Getter
@Setter
@ToString
public class Cliente {

    /**
     * Identificador único del cliente.
     * Este campo está mapeado a la columna "id" en la tabla "clientes".
     * Se genera automáticamente utilizando la estrategia {@code GenerationType.AUTO}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Relación uno a uno con la entidad {@code Usuario}.
     * Esta relación es bidireccional y está mapeada por el campo "cliente" en la clase {@code Usuario}.
     */
    @OneToOne(mappedBy = "cliente")
    private Usuario usuario;

    /**
     * Relación uno a uno con la entidad {@code Carrito}.
     * Esta relación es bidireccional y está mapeada por el campo "cliente" en la clase {@code Carrito}.
     * Se utiliza {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Carrito carrito;

    /**
     * Relación uno a uno con la entidad {@code DetallesCliente}.
     * Esta relación es bidireccional y está mapeada por el campo "cliente" en la clase {@code DetallesCliente}.
     * Se utiliza {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private DetallesCliente detallesCliente;

    /**
     * Relación uno a muchos con la entidad {@code ClienteEntrenamiento} para los entrenamientos del cliente.
     * Esta relación está mapeada por el campo "clienteEntrenado" en la clase {@code ClienteEntrenamiento}.
     * Se utiliza {@code FetchType.EAGER} para cargar las entidades de forma inmediata y {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToMany(mappedBy = "clienteEntrenado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientosCli;

    /**
     * Relación uno a muchos con la entidad {@code ClienteEntrenamiento} para los clientes que el usuario entrena.
     * Esta relación está mapeada por el campo "entrenadorCliente" en la clase {@code ClienteEntrenamiento}.
     * Se utiliza {@code FetchType.EAGER} para cargar las entidades de forma inmediata y {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToMany(mappedBy = "entrenadorCliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteEntrenamiento> clienteEntrenamientosEntrenador;

    /**
     * Relación uno a muchos con la entidad {@code ClienteDieta} para las dietas recetadas al cliente.
     * Esta relación está mapeada por el campo "cliente" en la clase {@code ClienteDieta}.
     * Se utiliza {@code FetchType.EAGER} para cargar las entidades de forma inmediata y {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteDieta> clienteDietasRecetado;

    /**
     * Relación uno a muchos con la entidad {@code ClienteDieta} para las dietas en las que el cliente es el dietista.
     * Esta relación está mapeada por el campo "dietista" en la clase {@code ClienteDieta}.
     * Se utiliza {@code FetchType.EAGER} para cargar las entidades de forma inmediata y {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToMany(mappedBy = "dietista", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClienteDieta> clienteDietasDietista;

    /**
     * Relación uno a muchos con la entidad {@code SesionClientes} para las sesiones en las que el cliente es citado.
     * Esta relación está mapeada por el campo "cliente" en la clase {@code SesionClientes}.
     * Se utiliza {@code FetchType.EAGER} para cargar las entidades de forma inmediata y {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SesionClientes> sesionClientesCitado;

    /**
     * Relación uno a muchos con la entidad {@code SesionClientes} para las sesiones en las que el cliente es el empleado.
     * Esta relación está mapeada por el campo "empleado" en la clase {@code SesionClientes}.
     * Se utiliza {@code FetchType.LAZY} para cargar las entidades de forma diferida y {@code CascadeType.ALL} para aplicar operaciones en cascada.
     */
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SesionClientes> sesionClientesEmpleado;
}
