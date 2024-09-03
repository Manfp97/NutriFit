package com.eoi.NutriFit.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * La clase DetallesCliente representa la entidad que almacena información detallada sobre las características físicas
 * y hábitos de un cliente, como altura, peso, actividad física, y otros detalles relevantes para su perfil.
 *
 * <p>Esta clase está anotada con <code>@Entity</code>, lo que indica que es una entidad JPA mapeada a una tabla en la base de datos.</p>
 *
 * <p>Utiliza las anotaciones de Lombok <code>@Getter</code> y <code>@Setter</code> para generar automáticamente los métodos getter y setter.</p>
 *
 * <p>La tabla en la base de datos asociada a esta entidad se llama <code>detalles_clientes</code>.</p>
 *
 * @author Francisco José Conejo Barranco, Juan María Avecilla Parrilla, Manuel Fernández Pernía
 */
@Entity
@Getter
@Setter
@Table(name = "detalles_clientes")
public class DetallesCliente {

    /**
     * Identificador único de la entidad DetallesCliente.
     * Este campo es autogenerado y no puede ser nulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Altura del cliente en metros. Este valor no puede ser nulo.
     */
    @Column(name = "altura", nullable = false)
    private Double altura;

    /**
     * Peso del cliente en kilogramos. Este valor no puede ser nulo.
     */
    @Column(name = "peso", nullable = false)
    private Double peso;

    /**
     * Valor de bioimpedancia del cliente, que es una medida de la resistencia biológica del cuerpo,
     * utilizada para estimar la composición corporal.
     */
    @Column(name = "bio_impedancia")
    private Double bioImpedancia;

    /**
     * Fecha de nacimiento del cliente, almacenada como un objeto <code>LocalDateTime</code>.
     */
    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    /**
     * Nivel de actividad física del cliente, que describe el tipo y la frecuencia de ejercicio físico que realiza.
     */
    @Column(name = "actividad_fisica")
    private String actividadFisica;

    /**
     * Nivel de usuario del cliente, que puede representar la experiencia o competencia en el manejo de su salud o en su entrenamiento.
     */
    @Column(name = "nivel_usuario")
    private String nivelUsuario;

    /**
     * Género del cliente, almacenado como una cadena de texto.
     */
    @Column(name = "genero")
    private String genero;

    /**
     * Edad del cliente, almacenada como un valor entero.
     */
    @Column(name = "edad")
    private Integer edad;

    /**
     * Relación uno a uno con la entidad <code>Cliente</code>, que representa al cliente
     * al que pertenecen estos detalles.
     */
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
